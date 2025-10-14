package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;

@Component("CertificatePdfGenerator")
@RequiredArgsConstructor
public class CertificatePdfGenerator implements PdfGenerator {

  private final CertificatePdfFillService certificatePdfFillService;

  @Override
  public Pdf generate(Certificate certificate, PdfGeneratorOptions options) {

    if (options.hiddenElements() != null && !options.hiddenElements().isEmpty()) {
      throw new IllegalArgumentException("Cannot hide elements for template printing");
    }

    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      final var filledPdf = certificatePdfFillService.fillDocument(
          certificate,
          options.additionalInfoText(),
          options.citizenFormat()
      );

      filledPdf.getDocumentInformation().setTitle(getFileName(certificate));
      filledPdf.getDocumentCatalog().getAcroForm().flatten();
      filledPdf.setAllSecurityToBeRemoved(true);
      filledPdf.save(byteArrayOutputStream);
      filledPdf.close();

      return new Pdf(byteArrayOutputStream.toByteArray(), getFileName(certificate));

    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }

  private String getFileName(Certificate certificate) {
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

