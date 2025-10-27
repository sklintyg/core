package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

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

class QuestionSynkopeBeskrivningTest {

  private static final ElementId PARENT_ELEMENT_ID = new ElementId("parent");
  private static final FieldId PARENT_FIELD_ID = new FieldId("field");
  private static final ElementId ELEMENT_ID = new ElementId("11.8");

  @Test
  void shallIncludeId() {
    final var element = QuestionSynkopeBeskrivning.questionSynkopeBeskrivning(PARENT_ELEMENT_ID,
        PARENT_FIELD_ID);

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextField.builder()
        .name("Ange tidpunkt")
        .id(new FieldId("11.8"))
        .build();

    final var element = QuestionSynkopeBeskrivning.questionSynkopeBeskrivning(PARENT_ELEMENT_ID,
        PARENT_FIELD_ID);

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(PARENT_ELEMENT_ID)
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$parent"))
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$11.8"))
            .build(),
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 50))
            .build()
    );

    final var element = QuestionSynkopeBeskrivning.questionSynkopeBeskrivning(PARENT_ELEMENT_ID,
        PARENT_FIELD_ID);

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

    final var element = QuestionSynkopeBeskrivning.questionSynkopeBeskrivning(PARENT_ELEMENT_ID,
        PARENT_FIELD_ID);

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionSynkopeBeskrivning.questionSynkopeBeskrivning(PARENT_ELEMENT_ID,
        PARENT_FIELD_ID);

    assertEquals(new ElementMapping(new ElementId("11"), null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(PARENT_ELEMENT_ID)
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionSynkopeBeskrivning.questionSynkopeBeskrivning(PARENT_ELEMENT_ID,
          PARENT_FIELD_ID);

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

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

      final var element = QuestionSynkopeBeskrivning.questionSynkopeBeskrivning(PARENT_ELEMENT_ID,
          PARENT_FIELD_ID);

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(PARENT_ELEMENT_ID)
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionSynkopeBeskrivning.questionSynkopeBeskrivning(PARENT_ELEMENT_ID,
          PARENT_FIELD_ID);

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}