package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFunktionsnedsattningar.QUESTION_FUNKTIONSNEDSATTNINGAR_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFunktionsnedsattningar.QUESTION_FUNKTIONSNEDSATTNINGAR_ID;

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

class QuestionFunktionsnedsattningarTest {

  @Test
  void shouldIncludeId() {
    final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();
    assertEquals(QUESTION_FUNKTIONSNEDSATTNINGAR_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(QUESTION_FUNKTIONSNEDSATTNINGAR_FIELD_ID)
        .name(
            "Ange vilken/vilka funktionsnedsättningar patienten har till följd av sjukdom och om möjligt svårighetsgrad. Ange även vad din bedömning av funktionsnedsättningar baseras på. Beskriv relevanta undersökningsfynd, testresultat, utredningssvar eller andra uppgifter (exempelvis anamnesuppgifter) och hur du bedömer dem.")
        .build();

    final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_FUNKTIONSNEDSATTNINGAR_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "$35.1"
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
    final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();
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
      final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();
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
      final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();
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
      final var element = QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();
      final var shouldValidate = element.shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }
  }
}