package se.inera.intyg.certificateprintservice.playwright.certificate;

import static se.inera.intyg.certificateprintservice.playwright.Constants.CONTENT;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;

@Slf4j
public class CertificateToHtmlConverter {

  private CertificateToHtmlConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String convert(Resource template, Certificate certificate, Page headerPage,
      String certificateHtml)
      throws IOException {
    var doc = TemplateToDocumentConverter.convert(template, certificateHtml, headerPage,
        certificate.getMetadata());

    final var content = doc.getElementById(CONTENT);

    certificate.getCategories().forEach(category ->
        Objects.requireNonNull(content).appendChild(CategoryConverter.category(category)));

    log.debug(doc.html());
    return doc.html();
  }
}
