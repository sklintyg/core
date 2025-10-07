package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionKontakt.QUESTION_KONTAKT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionKontakt.QUESTION_KONTAKT_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionKontaktTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionKontakt.questionKontakt();
    assertEquals(QUESTION_KONTAKT_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxBoolean.builder()
        .id(QUESTION_KONTAKT_FIELD_ID)
        .name("Kontakt med arbetsgivaren")
        .label(
            "Jag önskar att arbetsgivaren kontaktar vårdenheten. Patienten har lämnat samtycke för kontakt mellan arbetsgivare och vårdgivare.")
        .selectedText("Ja")
        .unselectedText("Ej angivet")
        .build();

    final var element = QuestionKontakt.questionKontakt();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionKontakt.questionKontakt();
    final var expectedRules = List.of();
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionKontakt.questionKontakt();
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(false)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }
}
