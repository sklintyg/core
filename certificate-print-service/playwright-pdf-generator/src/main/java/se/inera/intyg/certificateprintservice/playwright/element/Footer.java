package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.FOOTER_STYLE;
import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Builder
@Getter
@EqualsAndHashCode
public class Footer {

  String applicationOrigin;

  public Element create() {
    return element(Tag.DIV)
        .appendChild(footer());
  }

  public Element footer() {
    return element(Tag.DIV)
        .appendChildren(List.of(footerInfo(), createPageNr()))
        .attr(STYLE, FOOTER_STYLE);
  }

  private Element footerInfo() {
    return element(Tag.DIV)
        .appendChildren(List.of(footerText(), link()));
  }

  private Element footerText() {
    return element(Tag.SPAN)
        .attr(STYLE, """
            display: block;
            margin-top: 5mm;
            margin-bottom: 2mm;
            """)
        .text(TextFactory.applicationOrigin(applicationOrigin));
  }

  private Element link() {
    return element(Tag.A)
        .attr("href", "https://inera.se")
        .text("www.inera.se");
  }

  private static Element createPageNr() {
    var pageNr = new Element(Tag.DIV.toString()).attr(STYLE, " margin-top: 5mm;");
    pageNr.appendChild(new Element(Tag.SPAN.toString()).addClass("pageNumber"));
    pageNr.appendChild(new Element(Tag.SPAN.toString()).text(" ("));
    pageNr.appendChild(new Element(Tag.SPAN.toString()).addClass("totalPages"));
    pageNr.appendChild(new Element(Tag.SPAN.toString()).text(")"));
    return pageNr;
  }

}
