package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Matrix;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;

public class CertificatePdfGenerator implements PdfGenerator {

  private final List<PdfCertificateValueGenerator> pdfValueGenerators = List.of(
      new FK7211PdfGenerator()
  );

  private final GeneralPdfGenerator generalPdfGenerator = new GeneralPdfGenerator();

  public Pdf generate(Certificate certificate, String additionalInfoText) {
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      final var pdfValueGenerator = pdfValueGenerators.stream()
          .filter(
              generator -> generator.getType().equals(certificate.certificateModel().id().type())
          )
          .findFirst().orElseThrow(() -> new IllegalStateException(
                  String.format(
                      "Could not find pdf generator for certificate with type: '%s'",
                      certificate.certificateModel().type().code()
                  )
              )
          );

      final var filledPdf = generalPdfGenerator.fillDocument(certificate, pdfValueGenerator);

      setMarginText(filledPdf, certificate, additionalInfoText);
      setSentText(filledPdf, certificate);
      setDraftWatermark(filledPdf, certificate);

      filledPdf.getDocumentInformation().setTitle(setFileName(certificate));
      filledPdf.getDocumentCatalog().getAcroForm().flatten();
      filledPdf.save(byteArrayOutputStream);
      filledPdf.close();

      return new Pdf(byteArrayOutputStream.toByteArray(), setFileName(certificate));

    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }

  private static void setSentText(PDDocument fk7211Pdf, Certificate certificate)
      throws IOException {
    if (certificate.sent() == null) {
      return;
    }

    PdfGeneratorTextToolkit.addSentText(fk7211Pdf, certificate);
    PdfGeneratorTextToolkit.addSentVisibilityText(fk7211Pdf);
  }

  private static void setMarginText(PDDocument fk7211Pdf, Certificate certificate,
      String additionalInfoText)
      throws IOException {

    if (certificate.status() != Status.SIGNED) {
      return;
    }

    PdfGeneratorTextToolkit.addText(
        fk7211Pdf,
        "Intygsid: %s. %s".formatted(certificate.id().id(), additionalInfoText),
        8,
        Matrix.getRotateInstance(Math.PI / 2, 600, 25),
        Color.black,
        30F,
        30F,
        false
    );
  }

  private static void setDraftWatermark(PDDocument fk7211Pdf, Certificate certificate)
      throws IOException {
    if (certificate.status() != Status.DRAFT) {
      return;
    }

    PdfGeneratorTextToolkit.addDraftWatermark(fk7211Pdf);
  }

  private String setFileName(Certificate certificate) {
    final var certificateName = certificate.certificateModel().name();
    final var timestamp = LocalDateTime.now()
        .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

    return String.format("%s_%s", certificateName, timestamp)
        .replace("å", "a")
        .replace("ä", "a")
        .replace("ö", "o")
        .replace(" ", "_")
        .replace("–", "")
        .replace("__", "_")
        .toLowerCase();
  }
}
