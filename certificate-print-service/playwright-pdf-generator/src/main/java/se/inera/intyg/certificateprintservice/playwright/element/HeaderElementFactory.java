package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.Base64;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;

public class HeaderElementFactory {

  private HeaderElementFactory() {
    throw new IllegalStateException("Utility class");
  }

  private static final String DRAFT_ALERT_MESSAGE = "Detta är en utskrift av ett elektroniskt intygsutkast och ska INTE skickas till %s.";
  private static final String SENT_ALERT_MESSAGE = "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren. Notera att intyget redan har skickats till %s.";
  private static final String SIGNED_ALERT_MESSAGE = "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.";
  private static final String PERSON_SAMORDNINGS_NR = "Person- /samordningsnr";

  public static Element recipientLogo(byte[] logoBytes) {
    final var logoBase64 = new String(Base64.getEncoder().encode(logoBytes));
    final var logo = element(Tag.IMG)
        .attr("src", "data:image/png;base64, " + logoBase64)
        .attr("alt", "recipient-logo")
        .attr(STYLE, "max-height: 15mm; max-width: 35mm;");

    return element(Tag.DIV).appendChild(logo);
  }

  public static Element personId(String personId) {
    final var id = element(Tag.DIV)
        .attr(STYLE, "float: right; text-align: right;")
        .appendChildren(List.of(
            element(Tag.P).attr(STYLE, "font-weight: bold;").appendText(PERSON_SAMORDNINGS_NR),
            element(Tag.P).appendText(personId))
        );
    return element(Tag.DIV)
        .attr(STYLE, "width: 100%")
        .appendChild(id);
  }

  public static Element title(String name, String type, String version) {
    return element(Tag.DIV)
        .attr(STYLE, """
            font-size: 14pt;
            padding-bottom: 1mm;
            border-bottom: black solid 1px;
            """)
        .appendChildren(List.of(
            element(Tag.SPAN)
                .attr(STYLE, "font-weight: bold;")
                .appendText(name),
            element(Tag.SPAN)
                .appendText(" (%s v%s)".formatted(type, version))
        ));
  }

  public static Element alert(String recipientName, boolean isDraft, boolean isSent) {
    final var alertMessage = alertMessage(recipientName, isDraft, isSent);
    return element(Tag.DIV)
        .appendChild(element(Tag.P).appendText(alertMessage))
        .attr(STYLE, """
            margin-top: 5mm;
            padding: 3mm 5mm;
            border: red solid 1px;
            """);
  }

  private static String alertMessage(String recipientName, boolean isDraft, boolean isSent) {
    if (isDraft) {
      return DRAFT_ALERT_MESSAGE.formatted(recipientName);
    }
    if (isSent) {
      return
          SENT_ALERT_MESSAGE.formatted(recipientName);
    }
    return SIGNED_ALERT_MESSAGE;
  }

}
