package se.inera.intyg.certificateprintservice.print.element;

import java.util.Base64;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;

public class ElementProvider {

  private ElementProvider() {
    throw new IllegalStateException("Utility class");
  }

  public static Element element(Tag tag) {
    return new Element(tag.toString());
  }

  public static Element logo(byte[] logoBytes) {
    final var logoWrapper = element(Tag.DIV);
    final var base64 = Base64.getEncoder().encode(logoBytes);
    final var logo = element(Tag.IMG)
        .attr("src", "data:image/png;base64, " + new String(base64))
        .attr("alt", "recipient-logo")
        .attr("style",
            "max-height: 15mm; max-width: 35mm; border: blue solid 1px;");
    logoWrapper.appendChild(logo);
    return logoWrapper;
  }
}
