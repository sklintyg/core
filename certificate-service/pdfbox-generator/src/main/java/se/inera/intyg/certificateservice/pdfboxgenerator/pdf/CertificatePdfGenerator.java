package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;

@Component("CertificatePdfGenerator")
@RequiredArgsConstructor
public class CertificatePdfGenerator implements PdfGenerator {

  private final CertificatePdfFillService certificatePdfFillService;

  public Pdf generate(Certificate certificate, String additionalInfoText, boolean isCitizenFormat) {

    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      final var filledPdf = certificatePdfFillService.fillDocument(
          certificate,
          additionalInfoText,
          isCitizenFormat
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
