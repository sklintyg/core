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
public class Watermark {

  @Builder.Default
  String watermarkText = "UTKAST";

  public Element create() {
    return element(Tag.DIV)
        .appendChild(watermark())
        .attr(STYLE, Constants.WATERMARK_STYLE);
  }

  private Element watermark() {
    return element(Tag.P).text(watermarkText);
  }

}
