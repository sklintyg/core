package se.inera.intyg.certificateprintservice.playwright.document;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.swing.text.html.HTML.Tag;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.springframework.core.io.Resource;

@Builder
@Getter
@EqualsAndHashCode
public class Document {

  Header header;
  Footer footer;
  Content content;
  LeftMarginInfo leftMarginInfo;
  RightMarginInfo rightMarginInfo;
  Watermark watermark;
  String certificateName;
  String certificateType;
  String certificateVersion;
  String tailWindScript;
  boolean isDraft;
  boolean isSent;

  public org.jsoup.nodes.Document create(Resource template, Page page) throws IOException {
    final var document = document(template);
    setTitle(document);
    setTailWindScript(document);
    setHeader(document, page);

    document.getElementById("footer").appendChild(footer.create());
    document.getElementById("content").appendChild(content.create());
    document.getElementById("leftMarginInfo").appendChild(leftMarginInfo.create());

    if (!isDraft) {
      document.getElementById("rightMarginInfo").appendChild(rightMarginInfo.create());
    }

    if (isDraft) {
      document.getElementById("watermark").appendChild(watermark.create());
    }

    return document;
  }

  private void setHeader(org.jsoup.nodes.Document document, Page page) {
    document.getElementById("header").appendChild(header.create());

    final var headerHeight = calculateHeaderHeight(page, document.html(), "header");
    document.getElementById("header-space")
        .attr(STYLE, "height: calc(%spx);".formatted(headerHeight));
  }

  private org.jsoup.nodes.Document document(Resource template) throws IOException {
    return Jsoup.parse(template.getInputStream(), StandardCharsets.UTF_8.name(),
        "", Parser.xmlParser());
  }

  private void setTailWindScript(org.jsoup.nodes.Document document) {
    final var script = "data:text/javascript;base64, %s".formatted(tailWindScript);
    document.getElementsByTag(Tag.SCRIPT.toString())
        .attr("src", script);
  }

  private void setTitle(org.jsoup.nodes.Document document) {
    final var titleElement = document.getElementById("title");
    final var title = "%s (%s v%s)".formatted(certificateName, certificateType, certificateVersion);
    titleElement.appendText(title);
  }


  private int calculateHeaderHeight(Page page, String header, String name) {
    page.setContent(header);
    //return (int) Math.round(page.getByTitle(name).boundingBox().height).;
    return (int) page.getByTitle(name).evaluate("node => node.offsetHeight");
  }

}
