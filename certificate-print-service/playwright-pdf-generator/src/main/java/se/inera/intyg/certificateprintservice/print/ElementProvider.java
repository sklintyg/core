package se.inera.intyg.certificateprintservice.print;

import java.util.Base64;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.print.element.InformationElementFactory;

public class ElementProvider {

  public static Element element(Tag tag) {
    return new Element(tag.toString());
  }

  public static Element recipientLogo(byte[] logoBytes) {
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

  public static Element pageHeader(byte[] logo) {

    final var pageHeader = new Element(Tag.DIV.toString()).attr("style", """
          margin: 10mm 20mm 10mm 20mm;
          display: flex;
          border: green solid 1px;
        """);
    pageHeader.appendChild(recipientLogo(logo));

    return pageHeader;
  }

  public static Element headerWrapper() {
    return element(Tag.DIV)
        .attr("style", "display: grid; width: 100%; font-size: 10pt;")
        .attr("title", "headerElement");

  }

  public static Element certificateHeader(String certificateTitle) {
    final var certificateHeader = new Element(Tag.DIV.toString()).attr("style",
        "margin: 0 20mm 10mm 20mm;");
    certificateHeader.appendChild(InformationElementFactory.title(certificateTitle));
    return certificateHeader;
  }
}
