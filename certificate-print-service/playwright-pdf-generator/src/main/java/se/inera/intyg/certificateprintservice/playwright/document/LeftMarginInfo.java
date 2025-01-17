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
public class LeftMarginInfo {

  String certificateType;
  String recipientName;

  public Element create() {
    return element(Tag.DIV)
        .appendChild(leftMarginInfo())
        .attr(STYLE, Constants.LEFT_MARGIN_INFO_STYLE);
  }

  private Element leftMarginInfo() {
    final var info = "%s - Fastställd av %s".formatted(certificateType, recipientName);
    return element(Tag.P).text(info);
  }

}
