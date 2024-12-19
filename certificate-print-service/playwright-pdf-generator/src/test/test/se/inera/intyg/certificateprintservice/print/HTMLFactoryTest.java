package se.inera.intyg.certificateprintservice.print;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueTable;

class HTMLFactoryTest {

  @Test
  void shouldReturnTableHtmlIfOneHeading() {
    final var result = HTMLFactory.table(
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
            + "</table>"
        , result.toString());
  }
}