package se.inera.intyg.certificateprintservice.playwright.document;

import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Category;
import se.inera.intyg.certificateprintservice.playwright.certificate.CategoryConverter;
import se.inera.intyg.certificateprintservice.playwright.element.ContentElementFactory;

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
        .appendChildren(List.of(
            ContentElementFactory.issuerInfo(issuerName, issuingUnit, issuingUnitInfo,
                signDate, isDraft),
            ContentElementFactory.certificateInformation(certificateName, description)));
  }

  private List<Element> content() {
    return categories.stream()
        .map(CategoryConverter::category)
        .toList();
  }

}
