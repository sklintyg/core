package se.inera.intyg.certificateprintservice.print.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.print.api.Question;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueText;

class ElementQuestionConverterTest {

  @Test
  void shouldReturnHtmlForQuestionWithoutSubQuestions() {
    final var result = ElementQuestionConverter.question(
        Question.builder()
            .id("ID")
            .name("Name Question")
            .value(ElementValueText.builder()
                .text("Example text for value")
                .build()
            )
            .subQuestions(Collections.emptyList())
            .build()
    );

    assertEquals(
        "[<h3 class=\"p-1\">Name Question</h3>, <p class=\"text-sm p-1\">Example text for value</p>]",
        result.toString()
    );
  }

  @Test
  void shouldReturnHtmlForQuestionWithSubQuestions() {
    final var result = ElementQuestionConverter.question(
        Question.builder()
            .id("ID")
            .name("Name Question")
            .value(ElementValueText.builder()
                .text("Example text for value")
                .build()
            )
            .subQuestions(
                List.of(
                    Question.builder()
                        .id("ID 2")
                        .name("Name Question 2")
                        .value(ElementValueText.builder()
                            .text("Example text for value 2")
                            .build()
                        )
                        .subQuestions(Collections.emptyList())
                        .build()
                )
            )
            .build()
    );

    assertEquals(
        "[<h3 class=\"p-1\">Name Question</h3>, <p class=\"text-sm p-1\">Example text for value</p>, <h3 class=\"p-1\">Name Question 2</h3>, <p class=\"text-sm p-1\">Example text for value 2</p>]",
        result.toString()
    );
  }
}