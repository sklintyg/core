package se.inera.intyg.certificateprintservice.playwright.pdf;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Component
public class TemplateToDocumentConverter implements InitializingBean {

  @Value("classpath:templates/tailwindCSS.js")
  private Resource tailwindScript;

  private String tailwindCSS;

  @Override
  public void afterPropertiesSet() throws Exception {
    tailwindCSS = new String(Base64.getEncoder().encode(tailwindScript.getContentAsByteArray()));
  }


  public Document convert(Resource template, String header, Page page, Metadata metadata)
      throws IOException {
    final var document = Jsoup.parse(template.getInputStream(), StandardCharsets.UTF_8.name(), "",
        Parser.xmlParser());

    setDocumentTitle(document, metadata);
    setDocumentScript(document);
    setPageMargin(document, page, header);
    return document;
  }

  private void setDocumentTitle(Document document, Metadata metadata) {
    final var title = document.getElementById("title");
    Objects.requireNonNull(title).appendText(TextFactory.title(metadata));
  }

  public String setDocumentScript(String html) {
    return html.replace("<script></script>",
        "<script src=\"data:text/javascript;base64, %s\"></script>".formatted(tailwindCSS));
  }

  private void setPageMargin(Document doc, Page page, String header) {
    final var styleElement = doc.getElementById(STYLE);

    if (styleElement == null) {
      throw new IllegalStateException("Style element is null");
    }

    final var headerHeight = calculateHeaderHeight(page, header);
    styleElement.appendText("""
        @page {
          margin: calc(%spx + 15mm) 20mm 15mm 20mm;
        }
        """.formatted(Math.round(headerHeight)));
  }

  private double calculateHeaderHeight(Page page, String header) {
    page.setContent(header);
    return page.getByTitle("headerElement").boundingBox().height;
  }
}
