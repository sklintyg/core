package se.inera.intyg.certificateprintservice.playwright.document;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import javax.swing.text.html.HTML.Tag;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.playwright.Constants;

@Builder
@Getter
@EqualsAndHashCode
public class RightMarginInfo {

  String certificateId;

  public Element create() {
    return element(Tag.DIV)
        .appendChild(rightMarginInfo())
        .attr(STYLE, Constants.RIGHT_MARGIN_INFO_STYLE);
  }

  private Element rightMarginInfo() {
    final var info = "Intygs-ID: %s".formatted(certificateId);
    return element(Tag.P).text(info);
  }

}
