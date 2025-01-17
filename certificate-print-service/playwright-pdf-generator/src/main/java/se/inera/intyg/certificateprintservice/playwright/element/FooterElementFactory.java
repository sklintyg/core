package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;

public class FooterElementFactory {

  private static final String LINK_URL = "https://inera.se";
  private static final String LINK_TEXT = "www.inera.se";
  private static final String FOOTER_INFO_TEXT = "Utskriften skapades med %s - en tjänst som drivs av Inera AB";

  private FooterElementFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static Element info(String origin) {
    return element(Tag.DIV)
        .appendChildren(List.of(
            element(Tag.SPAN)
                .text(FOOTER_INFO_TEXT.formatted(origin))
                .attr(STYLE, """
                    display: block;
                    margin-top: 5mm;
                    margin-bottom: 2mm;
                    """),
            element(Tag.A).attr("href", LINK_URL).text(LINK_TEXT)
        ));
  }

}
