package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.CertificatePdfContextFactory;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.service.CertificatePdfFillService;

@Component("certificatePdfGenerator")
@RequiredArgsConstructor
public class CertificatePdfGenerator implements PdfGenerator {

  private final CertificatePdfFillService certificatePdfFillService;
  private final CertificatePdfContextFactory certificatePdfContextFactory;

  @Override
  public Pdf generate(Certificate certificate, PdfGeneratorOptions options) {

    if (!(certificate.certificateModel()
        .pdfSpecification() instanceof TemplatePdfSpecification templatePdfSpecification)) {
      throw new IllegalArgumentException(
          "CertificatePdfFillService can only process TemplatePdfSpecification");
    }

    if (options.hiddenElements() != null && !options.hiddenElements().isEmpty()) {
      throw new IllegalArgumentException("Cannot hide elements for template printing");
    }

    try (final var context = certificatePdfContextFactory.create(certificate, options,
        templatePdfSpecification)) {

      certificatePdfFillService.fillDocument(context);

      final var document = context.getDocument();

      document.getDocumentInformation().setTitle(getFileName(certificate));
      document.getDocumentCatalog().getAcroForm().flatten();
      document.setAllSecurityToBeRemoved(true);

      return new Pdf(context.toByteArray(), getFileName(certificate));

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

