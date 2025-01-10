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
            <table class="text-sm" style="margin-left: 5mm; margin-right: 5mm;">
             <tr style="border-bottom: black solid 1px;">
              <th class="font-medium" style="padding-right: 10mm;">H1</th>
             </tr>
             <tr>
              <td>D1</td>
             </tr>
            </table>""",
        result.toString()
    );
  }

  @Test
  void shouldReturnParagraphWithContent() {
    assertEquals("""
            <p class="text-sm italic" style="padding-left: 5mm; padding-right: 5mm; margin-bottom: 0; margin-top: 0;">answer</p>""",
        BasicElementFactory.p("answer").toString());

  }
}