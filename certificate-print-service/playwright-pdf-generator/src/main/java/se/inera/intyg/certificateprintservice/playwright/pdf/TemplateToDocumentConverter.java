package se.inera.intyg.certificateprintservice.playwright.pdf;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.core.io.Resource;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

public class TemplateToDocumentConverter {

  private TemplateToDocumentConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Document convert(Resource template, String header, Page page,
      Metadata metadata)
      throws IOException {
    final var headerHeight = getHeaderHeight(page, header);
    final var document = Jsoup.parse(template.getFile(), StandardCharsets.UTF_8.name(), "",
        Parser.xmlParser());

    setPageMargin(document, headerHeight);
    final var title = document.getElementById("title");
    Objects.requireNonNull(title).appendText(TextFactory.title(metadata));
    return document;
  }

  private static double getHeaderHeight(Page page, String header) {
    page.setContent(header);
    return page.getByTitle("headerElement").boundingBox().height;
  }

  private static void setPageMargin(Document doc, Double headerHeight) {
    final var styleElement = doc.getElementById("style");

    if (styleElement == null) {
      throw new IllegalStateException("Style element is null");
    }

    styleElement.appendText("""
        @page {
              margin: calc(%spx + 6mm) 20mm 15mm 20mm;
            }""".formatted(Math.round(headerHeight)));
  }
}
