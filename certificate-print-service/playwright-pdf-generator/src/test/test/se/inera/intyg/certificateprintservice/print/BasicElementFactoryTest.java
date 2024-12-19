package se.inera.intyg.certificateprintservice.print;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueTable;
import se.inera.intyg.certificateprintservice.print.element.BasicElementFactory;

class BasicElementFactoryTest {

  @Test
  void shouldReturnTableHtmlIfOneHeading() {
    final var result = BasicElementFactory.table(
        ElementValueTable.builder()
            .headings(List.of("H1"))
            .values(List.of(List.of("D1")))
            .build()
    );

    assertEquals(
        "<table>\n"
            + " <tr>\n"
            + "  <th>H1</th>\n"
            + " </tr>\n"
            + " <tr>\n"
            + "  <td>D1</td>\n"
            + " </tr>\n"
            + "</table>",
        result.toString()
    );
  }
}