package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;
import static se.inera.intyg.certificateprintservice.playwright.element.ElementProvider.element;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueTable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BasicElementFactory {

  public static Element table(ElementValueTable tableValue) {
    final var headerColumns = tableValue.getHeadings().size();
    final var valueColumns = tableValue.getValues().getFirst().size();
    final var tableElement = element(Tag.TABLE)
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
    final var trHeader = element(Tag.TR)
        .attr(STYLE, "border-bottom: black solid 1px;");
    for (int i = 0; i < valueColumns; i++) {
      final var th = element(Tag.TH)
          .addClass("font-bold")
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
        .addClass("text-sm italic px-[5mm]")
        .appendText(text);
  }

  private static Element tr(List<String> rowValues, int headerColumns,
      int valueColumns) {
    final var tableRow = element(Tag.TR);
    for (int i = 0; i < valueColumns; i++) {
      final var td = element(Tag.TD);
      if (valueColumns - i > headerColumns) {
        td.addClass("font-bold pr-[10mm]");
      }
      td.appendText(rowValues.get(i));
      tableRow.appendChild(td);
    }
    return tableRow;
  }

}
