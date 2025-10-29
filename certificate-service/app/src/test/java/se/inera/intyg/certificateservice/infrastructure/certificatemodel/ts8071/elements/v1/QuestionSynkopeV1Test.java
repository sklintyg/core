package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionSynkopeV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("11.7");

  @Test
  void shallIncludeId() {
    final var element = QuestionSynkopeV1.questionSynkopeV1();

    Assertions.assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har personen eller har personen haft någon synkope?")
        .description(
            "Med synkope avses här sådan som är utlöst av arytmi men även situationsutlöst synkope (till följd av exempelvis hosta, nysning, skratt eller ansträngning) och reflexsynkope (vasovagal synkope) som exempelvis utlösts av rädsla eller smärta.")
        .id(new FieldId("11.7"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionSynkopeV1.questionSynkopeV1();

    Assertions.assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRule = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($11.7)"
                )
            ).build(),
        ElementRuleExpression.builder()
            .id(new ElementId("11"))
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "$11.1"
                )
            ).build()

    );

    final var element = QuestionSynkopeV1.questionSynkopeV1();

    Assertions.assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionSynkopeV1.questionSynkopeV1();

    Assertions.assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionSynkopeV1.questionSynkopeV1();

    Assertions.assertEquals(new ElementMapping(new ElementId("11"), null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("11"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionSynkopeV1.questionSynkopeV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      Assertions.assertTrue(shouldValidate.test(elementData));
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

      final var element = QuestionSynkopeV1.questionSynkopeV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      Assertions.assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("11"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionSynkopeV1.questionSynkopeV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      Assertions.assertFalse(shouldValidate.test(elementData));
    }
  }
}