package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;

@Component
@RequiredArgsConstructor
public class CertificatePdfContextFactory {

  @Value("classpath:fonts/arialmt.ttf")
  private Resource fontResource;

  public CertificatePdfContext create(Certificate certificate, PdfGeneratorOptions options,
      TemplatePdfSpecification templatePdfSpecification) {
    final var template = getTemplatePath(certificate, options.citizenFormat(),
        templatePdfSpecification);

    try (final var in = getClass().getClassLoader().getResourceAsStream(template)) {
      if (in == null) {
        throw new IllegalStateException("Pdf template not found at path: " + template);
      }

      PDDocument document = Loader.loadPDF(in.readAllBytes());
      PDFont font = PDType0Font.load(document, fontResource.getInputStream());

      return CertificatePdfContext.builder()
          .document(document)
          .certificate(certificate)
          .templatePdfSpecification(templatePdfSpecification)
          .citizenFormat(options.citizenFormat())
          .additionalInfoText(options.additionalInfoText())
          .font(font)
          .defaultAppearance("/" + font.getName() + " 10.00 Tf 0 g")
          .mcid(new AtomicInteger(templatePdfSpecification.pdfMcid().value()))
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
