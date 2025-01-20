package se.inera.intyg.certificateprintservice.playwright.document;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.playwright.element.FooterElementFactory;

@Builder
@Getter
@EqualsAndHashCode
public class Footer {

  String applicationOrigin;

  public Element create() {
    return FooterElementFactory.info(applicationOrigin);
  }

}
