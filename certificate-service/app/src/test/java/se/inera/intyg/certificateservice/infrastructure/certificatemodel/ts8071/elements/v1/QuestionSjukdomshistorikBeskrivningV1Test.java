package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomshistorik;

class QuestionSjukdomshistorikBeskrivningV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("7.4");

  @Test
  void shallIncludeId() {
    final var element = QuestionSjukdomshistorikBeskrivningV1.questionSjukdomshistorikBeskrivningV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextField.builder()
        .name("Ange vad")
        .id(new FieldId("7.4"))
        .build();

    final var element = QuestionSjukdomshistorikBeskrivningV1.questionSjukdomshistorikBeskrivningV1();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("7.3"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$7.3"))
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$7.4"))
            .build(),
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 50))
            .build()
    );

    final var element = QuestionSjukdomshistorikBeskrivningV1.questionSjukdomshistorikBeskrivningV1();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(50)
            .build()
    );

    final var element = QuestionSjukdomshistorikBeskrivningV1.questionSjukdomshistorikBeskrivningV1();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("7.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionSjukdomshistorikBeskrivningV1.questionSjukdomshistorikBeskrivningV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("8.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionSjukdomshistorikBeskrivningV1.questionSjukdomshistorikBeskrivningV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("7.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionSjukdomshistorikBeskrivningV1.questionSjukdomshistorikBeskrivningV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionSjukdomshistorik.questionSjukdomshistorik();

    assertEquals(new ElementMapping(new ElementId("7"), null), element.mapping());
  }
}