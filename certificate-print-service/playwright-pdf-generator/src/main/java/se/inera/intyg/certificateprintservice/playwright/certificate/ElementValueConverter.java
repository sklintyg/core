package se.inera.intyg.certificateprintservice.playwright.certificate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValue;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueList;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueTable;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueText;
import se.inera.intyg.certificateprintservice.playwright.element.BasicElementFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementValueConverter {

  public static Element html(ElementValue elementValue) {
    if (elementValue instanceof ElementValueText textValue) {
      return BasicElementFactory.p(textValue.getText());
    } else if (elementValue instanceof ElementValueList listValue) {
      return BasicElementFactory.p(String.join(", ", listValue.getList()));
    } else if (elementValue instanceof ElementValueTable tableValue) {
      return BasicElementFactory.table(tableValue);
    }

    throw new IllegalStateException("No value converter for value type");
  }
}
