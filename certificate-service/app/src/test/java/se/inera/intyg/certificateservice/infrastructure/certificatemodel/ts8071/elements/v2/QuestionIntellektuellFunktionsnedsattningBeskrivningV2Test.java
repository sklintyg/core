package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionIntellektuellFunktionsnedsattningV2.QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionIntellektuellFunktionsnedsattningV2.QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionIntellektuellFunktionsnedsattningBeskrivningV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("26.2");

  @Test
  void shouldIncludeId() {
    final var element = QuestionIntellektuellFunktionsnedsattningBeskrivningV2.questionIntellektuellFunktionsnedsattningBeskrivningV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Vilken diagnos och grad?")
        .id(new FieldId("26.2"))
        .build();

    final var element = QuestionIntellektuellFunktionsnedsattningBeskrivningV2.questionIntellektuellFunktionsnedsattningBeskrivningV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID)
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression(
                "$" + QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_FIELD_ID.value()))
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$26.2"))
            .build(),
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 250))
            .build()
    );

    final var element = QuestionIntellektuellFunktionsnedsattningBeskrivningV2.questionIntellektuellFunktionsnedsattningBeskrivningV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(250)
            .build()
    );

    final var element = QuestionIntellektuellFunktionsnedsattningBeskrivningV2.questionIntellektuellFunktionsnedsattningBeskrivningV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionIntellektuellFunktionsnedsattningBeskrivningV2.questionIntellektuellFunktionsnedsattningBeskrivningV2();

    assertEquals(new ElementMapping(QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID, null),
        element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID)
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionIntellektuellFunktionsnedsattningBeskrivningV2.questionIntellektuellFunktionsnedsattningBeskrivningV2();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("other"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionIntellektuellFunktionsnedsattningBeskrivningV2.questionIntellektuellFunktionsnedsattningBeskrivningV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID)
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionIntellektuellFunktionsnedsattningBeskrivningV2.questionIntellektuellFunktionsnedsattningBeskrivningV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}

