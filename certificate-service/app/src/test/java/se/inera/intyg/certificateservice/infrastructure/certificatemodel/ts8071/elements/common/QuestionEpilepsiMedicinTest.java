package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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

class QuestionEpilepsiMedicinTest {

  private static final ElementId ELEMENT_ID = new ElementId("14.5");

  @Test
  void shallIncludeId() {
    final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har eller har personen haft någon krampförebyggande läkemedelsbehandling mot epilepsi?")
        .id(new FieldId("14.5"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRule = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($14.5)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("14"))
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "$14.1"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("14.3"))
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "$14.3"
                )
            )
            .build()
    );

    final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfOneOfQuestionsIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("14.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfBothQuestionsAreMissing() {
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

      final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfOneQuestionIsFalseAndOneMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("14.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnTrueIfOneQuestionIsFalseAndOneTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("14.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build(),
          ElementData.builder()
              .id(new ElementId("14"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnTrueIfBothQuestionsAreTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("14.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build(),
          ElementData.builder()
              .id(new ElementId("14"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionEpilepsiMedicin.questionEpilepsiMedicin();

    assertEquals(new ElementMapping(new ElementId("14"), null), element.mapping());
  }
}