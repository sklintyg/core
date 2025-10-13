package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionOvrigt.QUESTION_OVRIGT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionOvrigt.QUESTION_OVRIGT_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionOvrigtTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionOvrigt.questionOvrigt();
    assertEquals(QUESTION_OVRIGT_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(QUESTION_OVRIGT_FIELD_ID)
        .name("Övriga upplysningar till arbetsgivaren")
        .build();

    final var element = QuestionOvrigt.questionOvrigt();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionOvrigt.questionOvrigt();
    final var expectedRules = List.of();
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionOvrigt.questionOvrigt();
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(false)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }
}
