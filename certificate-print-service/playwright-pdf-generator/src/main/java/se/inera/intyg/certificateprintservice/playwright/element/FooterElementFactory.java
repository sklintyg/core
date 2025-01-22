package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FooterElementFactory {

  private static final String LINK_URL = "https://inera.se";
  private static final String LINK_TEXT = "www.inera.se";
  private static final String FOOTER_INFO_TEXT = "Utskriften skapades med %s - en tjänst som drivs av Inera AB";

  public static Element info(String origin) {
    return element(Tag.DIV)
        .appendChildren(List.of(
            element(Tag.P)
                .addClass("block mt-[5mm] mb-[2mm]")
                .text(FOOTER_INFO_TEXT.formatted(origin)),
            element(Tag.A)
                .addClass("text-[#0000ee]")
                .attr("href", LINK_URL).text(LINK_TEXT)
        ));
  }

}
