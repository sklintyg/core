package se.inera.intyg.certificateprintservice.playwright.document;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.playwright.element.HeaderElementFactory;

@Builder
@Value
@EqualsAndHashCode
public class Header {

  String certificateName;
  String certificateType;
  String certificateVersion;
  String personId;
  String recipientName;
  byte[] recipientLogo;
  boolean isDraft;
  boolean isSent;

  public Element create() {
    return element(Tag.DIV)
        .appendChild(header());
  }

  private Element header() {
    return element(Tag.DIV)
        .appendChild(pageHeader())
        .appendChild(certificateHeader());
  }

  private Element pageHeader() {
    return element(Tag.DIV)
        .appendChildren(List.of(
            HeaderElementFactory.recipientLogo(recipientLogo),
            HeaderElementFactory.personId(personId)))
        .attr(STYLE, """
              display: flex;
              top: 0; left: 0;
              margin: 0 0 10mm 0;
            """);
  }

  private Element certificateHeader() {
    return element(Tag.DIV)
        .attr(STYLE, "margin-bottom: 5mm;")
        .appendChildren(List.of(
            HeaderElementFactory.title(certificateName, certificateType, certificateVersion),
            HeaderElementFactory.alert(recipientName, isDraft, isSent)
        ));
  }

}
