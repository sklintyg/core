package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;

public class ContentElementFactory {

  private ContentElementFactory() {
    throw new IllegalStateException("Utility class");
  }

  private static final String ISSUER = "IntygsUtfärdare:";
  private static final String CONTACT_INFO = "Kontaktuppgifter:";
  private static final String SIGN_INFO = "Intyget signerades:";
  private static final String SEND_CERTIFICATE_TEXT = "Skicka intyg till mottagare";
  private static final String HANDLE_CERTIFICATE_TEXT = "Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren";

  public static Element issuerInfo(String name, String unit, List<String> unitInfo,
      String signDate, boolean isDraft) {
    final var issuerInfo = element(Tag.DIV)
        .attr(STYLE, "break-inside: avoid;");

    if (!isDraft) {
      issuerInfo.appendChild(issuerName(name));
    }

    issuerInfo.appendChild(contactInfo(unit, unitInfo));

    if (!isDraft) {
      issuerInfo.appendChild(signingDate(signDate));
    }

    return issuerInfo;
  }

  private static Element issuerName(String issuerName) {
    return element(Tag.DIV)
        .attr(STYLE, "display: grid; margin-top: 1cm;")
        .appendChildren(List.of(
            element(Tag.P).attr(STYLE, "font-weight: bold;").text(ISSUER),
            element(Tag.P).text(issuerName)));
  }

  private static Element contactInfo(String issuingUnit, List<String> issuingUnitInfo) {
    return element(Tag.DIV)
        .attr(STYLE, "display: grid; margin-top: 5mm;")
        .appendChildren(List.of(
            element(Tag.SPAN).attr(STYLE, "font-weight: bold;").text(CONTACT_INFO),
            element(Tag.SPAN).text(issuingUnit)))
        .appendChildren(issuingUnitInfo.stream().map(i -> element(Tag.SPAN).text(i))
            .toList());
  }

  private static Element signingDate(String signDate) {
    return element(Tag.DIV)
        .attr(STYLE, "display: grid; margin-top: 5mm;")
        .appendChildren(List.of(
            element(Tag.SPAN).attr(STYLE, "font-weight: bold;").text(SIGN_INFO),
            element(Tag.SPAN).text(signDate)));
  }

  public static Element certificateInformation(String name, String description) {
    final var certificateInfo = element(Tag.DIV)
        .attr(STYLE, "page-break-before:always")
        .appendChildren(List.of(
            element(Tag.STRONG).text(name),
            element(Tag.P).attr(STYLE, "white-space: pre-line;").append(description),
            element(Tag.BR)))
        .appendChildren(citizenInfo());

    setLinkColor(certificateInfo);
    return certificateInfo;
  }

  private static List<Element> citizenInfo() {
    return List.of(
        element(Tag.STRONG).text(SEND_CERTIFICATE_TEXT),
        element(Tag.P)
            .attr(STYLE, "white-space: pre-line;")
            .append(HANDLE_CERTIFICATE_TEXT));
  }

  private static void setLinkColor(Element infoWrapper) {
    infoWrapper.getAllElements().stream().flatMap(element -> element.children().stream())
        .filter(element -> element.tagName().equals(Tag.A.toString()))
        .forEach(element -> element.attr(STYLE, "color: #0000EE;"));
  }

}
