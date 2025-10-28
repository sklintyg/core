package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBalanssinne.QUESTION_BALANSSINNE_ID;

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

class QuestionBalanssinneBeskrivningV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("8.2");

  @Test
  void shouldIncludeId() {
    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Ange vilken typ av anfall och tidpunkt för senaste anfall")
        .id(new FieldId("8.2"))
        .build();

    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_BALANSSINNE_ID)
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$8.1"))
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$8.2"))
            .build(),
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 250))
            .build()
    );

    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

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

    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

    assertEquals(new ElementMapping(QUESTION_BALANSSINNE_ID, null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("8"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("7.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("8"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}

