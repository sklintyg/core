package se.inera.intyg.certificateprintservice.playwright.element;

import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;

public class ElementProvider {

  private ElementProvider() {
    throw new IllegalStateException("Utility class");
  }

  public static Element element(Tag tag) {
    return new Element(tag.toString());
  }

}
