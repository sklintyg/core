package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.helpers.PdfPatientInformationHelper;
import se.inera.intyg.certificateservice.pdfboxgenerator.helpers.PdfSignatureHelper;
import se.inera.intyg.certificateservice.pdfboxgenerator.helpers.PdfTextInformationHelper;
import se.inera.intyg.certificateservice.pdfboxgenerator.helpers.PdfUnitInformationHelper;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorTextToolkit;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorValueToolkit;

public class CertificatePdfGenerator implements PdfGenerator {

  private final List<PdfCertificateFillService> pdfValueGenerators = List.of(
      new FK7211PdfGenerator(),
      new FK7443PdfGenerator()
  );

  public Pdf generate(Certificate certificate, String additionalInfoText) {
    final var pdfGeneratorValueToolkit = new PdfGeneratorValueToolkit();
    final var pdfGeneratorTextToolkit = new PdfGeneratorTextToolkit();
    final var certificatePdfFillService = new CertificatePdfFillService(
        new PdfUnitInformationHelper(pdfGeneratorValueToolkit),
        new PdfPatientInformationHelper(pdfGeneratorValueToolkit),
        new PdfSignatureHelper(pdfGeneratorValueToolkit, pdfGeneratorTextToolkit),
        new PdfTextInformationHelper(pdfGeneratorTextToolkit));

    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      final var pdfValueGenerator = pdfValueGenerators.stream()
          .filter(
              generator -> generator.getType().equals(certificate.certificateModel().id().type())
          )
          .findFirst().orElseThrow(() -> new IllegalStateException(
                  String.format(
                      "Could not find pdf generator for certificate with type: '%s'",
                      certificate.certificateModel().id().type().type()
                  )
              )
          );

      final var filledPdf = certificatePdfFillService.fillDocument(
          certificate,
          additionalInfoText,
          pdfValueGenerator
      );

      filledPdf.getDocumentInformation().setTitle(setFileName(certificate));
      filledPdf.getDocumentCatalog().getAcroForm().flatten();
      filledPdf.save(byteArrayOutputStream);
      filledPdf.close();

      return new Pdf(byteArrayOutputStream.toByteArray(), setFileName(certificate));

    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
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
