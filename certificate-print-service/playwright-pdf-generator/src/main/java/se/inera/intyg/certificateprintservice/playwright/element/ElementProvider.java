package se.inera.intyg.certificateprintservice.playwright.element;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

public class ElementProvider {

  private static final String INVISIBLE_STYLE = "text-[#fffffe] h-px w-[17cm] text-[1px] -z-50 absolute";

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
            "max-height: 15mm; max-width: 35mm;");
    logoWrapper.appendChild(logo);
    return logoWrapper;
  }

  public static Collection<Element> accessibleHeader(Metadata metadata) {
    return List.of(
        element(Tag.P)
            .addClass(INVISIBLE_STYLE)
            .text("Person- /samordningsnr %s ".formatted(
                metadata.getPersonId())),

        element(Tag.H1)
            .addClass(INVISIBLE_STYLE)
            .text("%s (%s v%s)".formatted(metadata.getName(),
                metadata.getTypeId(),
                metadata.getVersion())),

        element(Tag.P)
            .addClass(INVISIBLE_STYLE)
            .text(TextFactory.alert(metadata))
    );
  }
}
