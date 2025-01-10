package se.inera.intyg.certificateprintservice.playwright.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueList;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueTable;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueText;
import se.inera.intyg.certificateprintservice.playwright.element.BasicElementFactory;

class ElementValueConverterTest {

  @Test
  void shouldReturnValueForText() {
    final var result = ElementValueConverter.html(
        ElementValueText.builder()
            .text("Example text for value")
            .build()
    );

    assertEquals(
        "<p class=\"text-sm italic\" style=\"padding-left: 5mm; padding-right: 5mm; margin-bottom: 0; margin-top: 0;\">Example text for value</p>",
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
        "<p class=\"text-sm italic\" style=\"padding-left: 5mm; padding-right: 5mm; margin-bottom: 0; margin-top: 0;\">Example text for value, Example 2</p>",
        result.toString()
    );
  }

  @Test
  void shouldReturnValueTable() {
    final var result = BasicElementFactory.table(
        ElementValueTable.builder()
            .headings(List.of("H1", "H2"))
            .values(List.of(List.of("D1", "D2"), List.of("D11", "D21")))
            .build()
    );

    assertEquals("""
            <table class="text-sm" style="margin-left: 5mm; margin-right: 5mm;">
             <tr style="border-bottom: black solid 1px;">
              <th class="font-medium" style="padding-right: 10mm;">H1</th>
              <th class="font-medium" style="padding-right: 10mm;">H2</th>
             </tr>
             <tr>
              <td>D1</td>
              <td>D2</td>
             </tr>
             <tr>
              <td>D11</td>
              <td>D21</td>
             </tr>
            </table>"""
        , result.toString());
  }

}