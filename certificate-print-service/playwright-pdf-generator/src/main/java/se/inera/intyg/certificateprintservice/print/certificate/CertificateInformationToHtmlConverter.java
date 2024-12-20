package se.inera.intyg.certificateprintservice.print.certificate;

import static se.inera.intyg.certificateprintservice.print.Constants.CONTENT;

import com.microsoft.playwright.Page;
import java.io.IOException;
import javax.swing.text.html.HTML.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import se.inera.intyg.certificateprintservice.print.api.Metadata;
import se.inera.intyg.certificateprintservice.print.element.ElementProvider;
import se.inera.intyg.certificateprintservice.print.pdf.TemplateToDocumentConverter;
import se.inera.intyg.certificateprintservice.print.text.TextFactory;

@Slf4j
public class CertificateInformationToHtmlConverter {

  private CertificateInformationToHtmlConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String convert(Resource template, Page infoHeaderPage,
      String certificateInfoHtml, Metadata metadata) throws IOException {

    var doc = TemplateToDocumentConverter.convert(template, certificateInfoHtml,
        infoHeaderPage,
        metadata);

    final var content = doc.getElementById(CONTENT);

    assert content != null;
    content.appendChild(ElementProvider.element(Tag.STRONG).text(metadata.getName()));
    content.appendChild(ElementProvider.element(Tag.P).text(metadata.getDescription()));
    content.appendChild(ElementProvider.element(Tag.STRONG).text("Skicka intyg till mottagare"));
    content.appendChild(ElementProvider.element(Tag.P).text(TextFactory.citizenInformation()));
    return doc.html();
  }
}
