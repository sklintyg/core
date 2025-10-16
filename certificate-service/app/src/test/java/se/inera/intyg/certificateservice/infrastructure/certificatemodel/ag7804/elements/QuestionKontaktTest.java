package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionKontakt.QUESTION_KONTAKT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionKontakt.QUESTION_KONTAKT_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionKontaktTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionKontakt.questionKontakt();
    assertEquals(QUESTION_KONTAKT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
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
  void shouldIncludeValidation() {
    final var element = QuestionKontakt.questionKontakt();
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(false)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldAlwaysReturnTrue() {
      final var element = QuestionKontakt.questionKontakt();
      final var shouldValidate = element.elementSpecification(QUESTION_KONTAKT_ID).shouldValidate();
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("26"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnTrueIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()
      );
      final var element = QuestionKontakt.questionKontakt();
      final var shouldValidate = element.elementSpecification(QUESTION_KONTAKT_ID).shouldValidate();
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnTrueIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("8.1"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      final var element = QuestionKontakt.questionKontakt();
      final var shouldValidate = element.elementSpecification(QUESTION_KONTAKT_ID).shouldValidate();
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      final var element = QuestionKontakt.questionKontakt();
      final var shouldValidate = element.elementSpecification(QUESTION_KONTAKT_ID).shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldNotIncludeWhenRenewing() {
      final var element = QuestionKontakt.questionKontakt();
      assertFalse(element.includeWhenRenewing());
    }
  }

}