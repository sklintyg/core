package se.inera.intyg.certificateprintservice.print;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueTable;

public class HTMLFactory {

  private HTMLFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static Element getTableValue(ElementValueTable tableValue) {
    final var tableElement = new Element(Tag.TABLE.toString());

    final var trHeader = new Element(Tag.TR.toString());
    tableValue.getHeadings().stream()
        .map(heading -> {
          final var th = new Element(Tag.TH.toString());
          th.appendText(heading);
          return th;
        })
        .forEach(trHeader::appendChild);
    tableElement.appendChild(trHeader);

    tableValue.getValues().stream()
        .map(HTMLFactory::getTableRow)
        .forEach(tableElement::appendChild);

    return tableElement;
  }

  private static Element getTableRow(List<String> rowValues) {
    final var trRow = new Element(Tag.TR.toString());
    rowValues.stream()
        .map(rowValue -> {
          final var td = new Element(Tag.TD.toString());
          td.appendText(rowValue);
          return td;
        })
        .forEach(trRow::appendChild);
    return trRow;
  }

}
