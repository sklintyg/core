package se.inera.intyg.certificateprintservice.playwright.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueTable;

class BasicElementFactoryTest {

  @Test
  void shouldReturnTableHtmlIfOneHeading() {
    final var result = BasicElementFactory.table(
        ElementValueTable.builder()
            .headings(List.of("H1"))
            .values(List.of(List.of("D1")))
            .build()
    );

    assertEquals("""
            <table>
             <tr style="border-bottom: black solid 1px;">
              <th style="padding-right: 5mm;">H1</th>
             </tr>
             <tr>
              <td>D1</td>
             </tr>
            </table>""",
        result.toString()
    );
  }
}