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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionBalanssinneBeskrivningV1V2Test {

  private static final ElementId ELEMENT_ID = new ElementId("8.2");

  @Test
  void shallIncludeId() {
    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Ange vilken typ av anfall och tidpunkt för senaste anfall")
        .id(new FieldId("8.2"))
        .build();

    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

    assertEquals(3, element.rules().size());
  }

  @Test
  void shallIncludeTextLimitRule() {
    final var expectedRule = ElementRuleLimit.builder()
        .id(ELEMENT_ID)
        .type(ElementRuleType.TEXT_LIMIT)
        .limit(new RuleLimit((short) 250))
        .build();

    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

    assertEquals(expectedRule, element.rules().get(2));
  }

  @Test
  void shallIncludeValidations() {
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
  void shallIncludeMapping() {
    final var element = QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2();

    assertEquals(new ElementMapping(QUESTION_BALANSSINNE_ID, null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsTrue() {
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
    void shallReturnFalseIfElementMissing() {
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
    void shallReturnFalseIfElementFalse() {
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

