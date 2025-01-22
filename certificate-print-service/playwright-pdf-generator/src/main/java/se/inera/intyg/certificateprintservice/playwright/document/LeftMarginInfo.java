package se.inera.intyg.certificateprintservice.playwright.document;

import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import javax.swing.text.html.HTML.Tag;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jsoup.nodes.Element;

@Builder
@Getter
@EqualsAndHashCode
public class LeftMarginInfo {

  String certificateType;
  String recipientName;

  private static final String LEFT_MARGIN_TEXT = "%s - Fastställd av %s";

  public Element create() {
    return element(Tag.DIV).appendChild(leftMarginInfo());
  }

  private Element leftMarginInfo() {
    final var info = LEFT_MARGIN_TEXT.formatted(certificateType, recipientName);
    return element(Tag.P).text(info);
  }

}
