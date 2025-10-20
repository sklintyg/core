package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentElementFactory {

  private static final String ISSUER = "Intygsutfärdare:";
  private static final String CONTACT_INFO = "Kontaktuppgifter:";
  private static final String SIGN_INFO = "Intyget signerades:";
  private static final String SEND_CERTIFICATE_TEXT = "Skicka intyg till mottagare";
  private static final String HANDLE_CERTIFICATE_TEXT = "Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren";
  private static final String SEND_CERTIFICATE_TEXT_HANDLE_CERTIFICATE = "Hantera intyg";
  private static final String HANDLE_CERTIFICATE_TEXT_HANDLE_CERTIFICATE = "Du kan hantera ditt intyg genom att logga in på 1177.se";

  private static final String INVISIBLE_STYLE = "absolute h-px w-[17cm] text-[1px] -z-50 text-white";

  public static Element hiddenAccessibleHeader(String name, String type, String version,
      String recipientName, String personId, boolean isDraft,
      boolean isSent) {
    return element(Tag.DIV)
        .appendChildren(List.of(
            element(Tag.P)
                .addClass(INVISIBLE_STYLE)
                .text("Person- /samordningsnr %s".formatted(personId)),
            element(Tag.H1)
                .addClass(INVISIBLE_STYLE)
                .text("%s (%s v%s)".formatted(name, type, version)),
            element(Tag.P)
                .addClass(INVISIBLE_STYLE)
                .text(HeaderElementFactory.alertMessage(recipientName, isDraft, isSent))
        ));
  }

  public static Element issuerInfo(String name, String unit, List<String> unitInfo,
      String signDate, boolean isDraft) {
    final var issuerInfo = element(Tag.DIV)
        .addClass("break-inside-avoid");

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
        .addClass("grid mt-[5mm]")
        .appendChildren(List.of(
            element(Tag.P).addClass("font-bold").text(ISSUER),
            element(Tag.P).text(issuerName)));
  }

  private static Element contactInfo(String issuingUnit, List<String> issuingUnitInfo) {
    return element(Tag.DIV)
        .addClass("grid mt-[5mm]")
        .appendChildren(List.of(
            element(Tag.P).addClass("font-bold").text(CONTACT_INFO),
            element(Tag.P).text(issuingUnit)))
        .appendChildren(issuingUnitInfo.stream().map(i -> element(Tag.P).text(i))
            .toList());
  }

  private static Element signingDate(String signDate) {
    return element(Tag.DIV)
        .addClass("grid mt-[5mm]")
        .appendChildren(List.of(
            element(Tag.P).addClass("font-bold").text(SIGN_INFO),
            element(Tag.P).text(signDate)));
  }

  public static Element certificateInformation(String name, String description,
      boolean canSendElectronically) {
    final var certificateInfo = element(Tag.DIV)
        .addClass("break-before-page")
        .appendChildren(List.of(
            element(Tag.STRONG).text(name),
            element(Tag.P).addClass("whitespace-pre-line").append(description),
            element(Tag.BR)))
        .appendChildren(citizenInfo(canSendElectronically));

    setLinkColor(certificateInfo);
    return certificateInfo;
  }

  private static List<Element> citizenInfo(boolean canSendElectronically) {
    if (!canSendElectronically) {
      return List.of(
          element(Tag.STRONG).text(SEND_CERTIFICATE_TEXT_HANDLE_CERTIFICATE),
          element(Tag.P)
              .addClass("whitespace-pre-line")
              .append(HANDLE_CERTIFICATE_TEXT_HANDLE_CERTIFICATE));

    }
    return List.of(
        element(Tag.STRONG).text(SEND_CERTIFICATE_TEXT),
        element(Tag.P)
            .addClass("whitespace-pre-line")
            .append(HANDLE_CERTIFICATE_TEXT));
  }

  private static void setLinkColor(Element infoWrapper) {
    infoWrapper.getAllElements().stream().flatMap(element -> element.children().stream())
        .filter(element -> element.tagName().equals(Tag.A.toString()))
        .forEach(element -> element.addClass("text-[#0000ee] underline"));
  }

}
