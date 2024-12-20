package se.inera.intyg.certificateprintservice.playwright.certificate;

import static se.inera.intyg.certificateprintservice.playwright.Constants.CONTENT;

import com.microsoft.playwright.Page;
import java.io.IOException;
import javax.swing.text.html.HTML.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.element.ElementProvider;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class CertificateInformationToHtmlConverter {

  private final TemplateToDocumentConverter templateToDocumentConverter;

  public String convert(Resource template, Page infoHeaderPage,
      String certificateInfoHtml, Metadata metadata) throws IOException {

    var doc = templateToDocumentConverter.convert(template, certificateInfoHtml,
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
