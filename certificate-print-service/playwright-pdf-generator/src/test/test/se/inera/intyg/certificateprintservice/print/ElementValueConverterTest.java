package se.inera.intyg.certificateprintservice.print;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueList;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueTable;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueText;

class ElementValueConverterTest {

  @Test
  void shouldReturnValueForText() {
    final var result = ElementValueConverter.html(
        ElementValueText.builder()
            .text("Example text for value")
            .build()
    );

    assertEquals(
        "<p class=\"text-sm p-1\">Example text for value</p>",
        result.toString()
    );
  }

  @Test
  void shouldReturnValueForList() {
    final var result = ElementValueConverter.html(
        ElementValueList.builder()
            .list(List.of("Example text for value", "Example 2"))
            .build()
    );

    assertEquals(
        "<p class=\"text-sm p-1\">Example text for value, Example 2</p>",
        result.toString()
    );
  }

  @Test
  void shouldReturnValueTable() {
    final var result = HTMLFactory.table(
        ElementValueTable.builder()
            .headings(List.of("H1", "H2"))
            .values(List.of(List.of("D1", "D2"), List.of("D11", "D21")))
            .build()
    );

    assertEquals(
        "<table>\n"
            + " <tr>\n"
            + "  <th>H1</th>\n"
            + "  <th>H2</th>\n"
            + " </tr>\n"
            + " <tr>\n"
            + "  <td>D1</td>\n"
            + "  <td>D2</td>\n"
            + " </tr>\n"
            + " <tr>\n"
            + "  <td>D11</td>\n"
            + "  <td>D21</td>\n"
            + " </tr>\n"
            + "</table>"
        , result.toString());
  }

}