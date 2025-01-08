package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueTable;

public class BasicElementFactory {

  private BasicElementFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static Element table(ElementValueTable tableValue) {
    final var headerColumns = tableValue.getHeadings().size();
    final var valueColumns = tableValue.getValues().getFirst().size();
    final var tableElement = ElementProvider.element(Tag.TABLE)
        .addClass("text-sm")
        .attr(STYLE, "margin-left: 5mm; margin-right: 5mm;");

    tableElement.appendChild(th(tableValue, headerColumns, valueColumns));

    tableValue.getValues().stream()
        .map(values -> tr(values, headerColumns, valueColumns))
        .forEach(tableElement::appendChild);

    return tableElement;
  }

  private static Element th(ElementValueTable tableValue, int headerColumns,
      int valueColumns) {
    final var trHeader = ElementProvider.element(Tag.TR)
        .attr(STYLE, "border-bottom: black solid 1px;");
    for (int i = 0; i < valueColumns; i++) {
      final var th = ElementProvider.element(Tag.TH)
          .addClass("font-medium")
          .attr(STYLE, "padding-right: 10mm;");
      if (valueColumns - i > headerColumns) {
        th.appendText("");
      } else {
        th.appendText(tableValue.getHeadings().get(i - (valueColumns - headerColumns)));
      }
      trHeader.appendChild(th);
    }
    return trHeader;
  }

  public static Element p(String text) {
    return new Element(Tag.P.toString())
        .addClass("text-sm italic")
        .attr(STYLE, "padding-left: 5mm; padding-right: 5mm; margin-bottom: 0; margin-top: 0;")
        .appendText(text);
  }

  private static Element tr(List<String> rowValues, int headerColumns,
      int valueColumns) {
    final var tableRow = ElementProvider.element(Tag.TR);
    for (int i = 0; i < valueColumns; i++) {
      final var td = ElementProvider.element(Tag.TD);
      if (valueColumns - i > headerColumns) {
        td.addClass("font-medium");
        td.attr(STYLE, "padding-right: 10mm;");
      }
      td.appendText(rowValues.get(i));
      tableRow.appendChild(td);
    }
    return tableRow;
  }

}
