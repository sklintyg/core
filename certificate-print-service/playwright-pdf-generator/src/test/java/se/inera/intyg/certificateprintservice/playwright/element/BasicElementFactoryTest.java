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
            <table class="text-sm mx-[5mm]">
             <tr class="border-b border-black border-solid">
              <th class="font-bold pr-[10mm]">H1</th>
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
    assertEquals("<p class=\"text-sm italic px-[5mm]\">answer</p>",
        BasicElementFactory.p("answer").toString());

  }
}