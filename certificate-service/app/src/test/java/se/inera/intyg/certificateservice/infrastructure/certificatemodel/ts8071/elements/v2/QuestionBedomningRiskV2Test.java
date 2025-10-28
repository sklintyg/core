package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs002.YES;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomning.QUESTION_BEDOMNING_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
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

class QuestionBedomningRiskV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("23.3");

  @Test
  void shouldIncludeId() {
    final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Du bedömer att det finns en risk, ange orsaken till detta")
        .id(new FieldId("23.3"))
        .build();

    final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$23.3"))
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_BEDOMNING_ID)
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$ja"))
            .build(),
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 250))
            .build()
    );

    final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

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

    final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

    assertEquals(new ElementMapping(QUESTION_BEDOMNING_ID, YES), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfCodeIsYes() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_BEDOMNING_ID)
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId(YES.code()))
                      .build()
              )
              .build()
      );

      final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("other"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId(YES.code()))
                      .build()
              )
              .build()
      );

      final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfCodeIsNotYes() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_BEDOMNING_ID)
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId("NO"))
                      .build()
              )
              .build()
      );

      final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}

