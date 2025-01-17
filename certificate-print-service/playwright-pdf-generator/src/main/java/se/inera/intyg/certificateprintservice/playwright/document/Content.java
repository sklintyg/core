package se.inera.intyg.certificateprintservice.playwright.document;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Category;
import se.inera.intyg.certificateprintservice.playwright.certificate.CategoryConverter;
import se.inera.intyg.certificateprintservice.playwright.element.InformationElementFactory;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Builder
@Getter
@EqualsAndHashCode
public class Content {

  List<Category> categories;
  String issuerName;
  String issuingUnit;
  List<String> issuingUnitInfo;
  String signDate;
  String description;
  String certificateName;
  boolean isDraft;

  public Element create() {
    return element(Tag.DIV)
        .appendChildren(content())
        .attr(STYLE, "margin: 0 20mm;");
  }

  private List<Element> content() {
    final var content = new ArrayList<>(categories.stream()
        .map(CategoryConverter::category)
        .toList());

    content.add(getIssuerInformation());
    content.add(certificateInformation());
    return content;
  }

  private Element certificateInformation() {
    final var infoWrapper = element(Tag.DIV)
        .attr("style", "page-break-before:always")
        .appendChild(certificateName(certificateName))
        .appendChild(certificateDescription(description))
        .appendChild(element(Tag.BR))
        .appendChildren(citizenInfo());

    setLinkColor(infoWrapper);
    return infoWrapper;
  }

  private void setLinkColor(Element infoWrapper) {
    infoWrapper.getAllElements().stream().flatMap(element -> element.children().stream())
        .filter(element -> element.tagName().equals(Tag.A.toString()))
        .forEach(element -> element.attr(STYLE, "color: #0000EE;"));
  }

  private Collection<Node> citizenInfo() {
    return List.of(
        element(Tag.STRONG).text("Skicka intyg till mottagare"),
        element(Tag.P)
            .attr(STYLE, "white-space: pre-line;")
            .append(TextFactory.citizenInformation()));
  }

  private Node certificateDescription(String description) {
    return element(Tag.P)
        .attr(STYLE, "white-space: pre-line;")
        .append(description);
  }

  private Node certificateName(String name) {
    return element(Tag.STRONG).text(name);
  }

  private Element getIssuerInformation() {
    final var issuerInfo = element(Tag.DIV)
        .attr(STYLE, "break-inside: avoid;");

    if (!isDraft) {
      issuerInfo.appendChild(InformationElementFactory.issuerName(issuerName));
    }

    issuerInfo.appendChild(InformationElementFactory.contactInfo(issuingUnit, issuingUnitInfo));

    if (!isDraft) {
      issuerInfo.appendChild(InformationElementFactory.signingDate(signDate));
    }

    return issuerInfo;
  }


}
