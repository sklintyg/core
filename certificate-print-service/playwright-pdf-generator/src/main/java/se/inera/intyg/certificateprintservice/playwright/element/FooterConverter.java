package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.ArrayList;
import javax.swing.text.html.HTML.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Slf4j
public class FooterConverter {

  private FooterConverter() {
    throw new IllegalStateException("Utility class");
  }


  public static String createFooter(Metadata metadata) {
    final var elementList = new ArrayList<Element>();
    final var baseWrapper = element(Tag.DIV);

    final var elementWrapper = element(Tag.DIV);
    elementList.add(createFooterText(metadata));
    elementList.add(createLink());
    elementWrapper.appendChildren(elementList);

    final var footerWrapper = element(Tag.DIV)
        .attr(STYLE, """
            height: 25mm;
            width: 100%;
            font-size: 10pt;
            margin: 0 20mm;
            border-top: black solid 1px;
            justify-content: space-between;
            display: flex;
            """);
    footerWrapper.appendChild(elementWrapper);
    footerWrapper.appendChild(createPageNr());

    baseWrapper.appendChild(footerWrapper);
    return baseWrapper.html();
  }

  private static Element createLink() {
    return element(Tag.A)
        .attr("href", "https://inera.se")
        .attr("alt", "This is alt text")
        .text("www.inera.se");
  }

  private static Element createFooterText(Metadata metadata) {
    return element(Tag.SPAN)
        .attr(STYLE, """
            display: block;
            margin-top: 5mm;
            margin-bottom: 2mm;
            """)
        .text(TextFactory.applicationOrigin(metadata));
  }

  private static Node createPageNr() {
    var pageNr = new Element(Tag.DIV.toString()).attr(STYLE, " margin-top: 5mm;");
    pageNr.appendChild(new Element(Tag.SPAN.toString()).addClass("pageNumber"));
    pageNr.appendChild(new Element(Tag.SPAN.toString()).text(" ("));
    pageNr.appendChild(new Element(Tag.SPAN.toString()).addClass("totalPages"));
    pageNr.appendChild(new Element(Tag.SPAN.toString()).text(")"));
    return pageNr;
  }
}
