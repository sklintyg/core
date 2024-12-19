package se.inera.intyg.certificateprintservice.print;

import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValue;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueList;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueTable;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueText;

public class ElementValueConverter {

  private ElementValueConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Element html(ElementValue elementValue) {
    if (elementValue instanceof ElementValueText textValue) {
      return HTMLFactory.p(textValue.getText());
    } else if (elementValue instanceof ElementValueList listValue) {
      return HTMLFactory.p(String.join(", ", listValue.getList()));
    } else if (elementValue instanceof ElementValueTable tableValue) {
      return HTMLFactory.table(tableValue);
    }

    throw new IllegalStateException("No value converter for value type");
  }
}
