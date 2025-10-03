package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionAktivitetsbegransningar.QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionAktivitetsbegransningar.QUESTION_AKTIVITETSBEGRANSNING_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionAktivitetsbegransningarTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();
    assertEquals(QUESTION_AKTIVITETSBEGRANSNING_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID)
        .name(
            "Beskriv vad du bedömer att patienten har svårt att göra på grund av sin sjukdom. Ange exempel på sådana begränsningar relaterade till de arbetsuppgifter eller annan sysselsättning som du bedömer arbetsförmågan i förhållande till. Ange om möjligt svårighetsgrad.")
        .build();

    final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_AKTIVITETSBEGRANSNING_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "$17.1"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("27"))
            .type(ElementRuleType.HIDE)
            .expression(new RuleExpression("$27.1"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()
      );
      final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();
      final var shouldValidate = element.shouldValidate();
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnTrueIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("7"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();
      final var shouldValidate = element.shouldValidate();
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
      final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();
      final var shouldValidate = element.shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }
  }

}