package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfContext;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfFieldSanitizer;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfFontResolver;

@Component
@RequiredArgsConstructor
public class CertificatePdfContextFactory {

  private final TextFieldAppearanceFactory textFieldAppearanceFactory;

  public CertificatePdfContext create(Certificate certificate, PdfGeneratorOptions options,
      TemplatePdfSpecification templatePdfSpecification) {
    final var template = getTemplatePath(certificate, options.citizenFormat(),
        templatePdfSpecification);

    try (final var in = getClass().getClassLoader().getResourceAsStream(template)) {
      if (in == null) {
        throw new IllegalStateException("Pdf template not found at path: " + template);
      }

      PDDocument document = Loader.loadPDF(in.readAllBytes());

      return CertificatePdfContext.builder()
          .document(document)
          .certificate(certificate)
          .templatePdfSpecification(templatePdfSpecification)
          .citizenFormat(options.citizenFormat())
          .additionalInfoText(options.additionalInfoText())
          .mcid(new AtomicInteger(templatePdfSpecification.pdfMcid().value()))
          .fieldSanitizer(new PdfFieldSanitizer())
          .fontResolver(new PdfFontResolver(
              document.getDocumentCatalog().getAcroForm(),
              textFieldAppearanceFactory))
          .build();
    } catch (IOException e) {
      throw new IllegalStateException("Could not load pdf template from path: " + template, e);
    }
  }


  private String getTemplatePath(Certificate certificate, boolean isCitizenFormat,
      TemplatePdfSpecification templatePdfSpecification) {
    return includeAddress(certificate, isCitizenFormat)
        ? templatePdfSpecification.pdfTemplatePath()
        : templatePdfSpecification.pdfNoAddressTemplatePath();
  }

  private static boolean includeAddress(Certificate certificate, boolean isCitizenFormat) {
    if (isCitizenFormat) {
      return false;
    }

    return certificate.sent() == null || certificate.sent().sentAt() == null;
  }

}
