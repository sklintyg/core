package se.inera.intyg.certificateprintservice.playwright.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Category;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Question;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueText;

class ElementCategoryConverterTest {

  @Test
  void shouldReturnHtmlForCategory() {
    final var result = CategoryConverter.category(
        Category.builder()
            .id("ID")
            .name("Name Category")
            .questions(List.of(
                Question.builder()
                    .id("ID")
                    .name("Name Question")
                    .value(ElementValueText.builder()
                        .text("Example text for value")
                        .build()
                    )
                    .subQuestions(Collections.emptyList())
                    .build()
            ))
            .build()
    );

    assertEquals(
        """
            <div class="box-decoration-clone" style="border: 1px solid black; margin-bottom: 5mm;">
             <h2 class="text-lg font-bold uppercase" style="border-bottom: 1px solid black;">Name Category</h2>
             <h3 class="p-1">Name Question</h3>
             <p class="text-sm p-1">Example text for value</p>
            </div>""",
        result.toString()
    );
  }
}