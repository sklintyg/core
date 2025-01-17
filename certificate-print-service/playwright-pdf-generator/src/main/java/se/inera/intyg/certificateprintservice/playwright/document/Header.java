package se.inera.intyg.certificateprintservice.playwright.document;

import static se.inera.intyg.certificateprintservice.playwright.Constants.HEADER_STYLE;
import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import javax.swing.text.html.HTML.Tag;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.playwright.element.ElementProvider;
import se.inera.intyg.certificateprintservice.playwright.element.InformationElementFactory;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

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
    return element(Tag.DIV).attr(STYLE, HEADER_STYLE)
        .appendChild(pageHeader())
        .appendChild(certificateHeader());
  }

  private Element pageHeader() {
    return element(Tag.DIV)
        .appendChild(ElementProvider.logo(recipientLogo))
        .appendChild(InformationElementFactory.personId(personId))
        .attr(STYLE, """
              border: green solid 1px;
              top: 0; left: 0;
              margin: 0 0 10mm 0;
              display: flex;
            """);
  }

  private Element certificateHeader() {
    return element(Tag.DIV)
        .attr(STYLE, "margin-bottom: 5mm; border: red solid 1px;\n")
        .appendChild(
            InformationElementFactory.title(certificateName, certificateType, certificateVersion))
        .appendChild(
            InformationElementFactory.alert(TextFactory.alert(recipientName, isDraft, isSent)));
  }

}
