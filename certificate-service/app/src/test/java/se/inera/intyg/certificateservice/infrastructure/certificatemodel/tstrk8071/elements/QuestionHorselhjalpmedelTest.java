package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.TAXI;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionHorselhjalpmedelTest {

  private static final ElementId ELEMENT_ID = new ElementId("9.2");

  @Test
  void shallIncludeId() {
    final var element = QuestionHorselhjalpmedel.questionHorselhjalpmedel();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Behöver hörapparat användas?")
        .id(new FieldId("9.2"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionHorselhjalpmedel.questionHorselhjalpmedel();

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
                    "exists($9.2)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("1"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("exists(gr_II_III) || exists(tax_leg)"))
            .build()
    );

    final var element = QuestionHorselhjalpmedel.questionHorselhjalpmedel();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionHorselhjalpmedel.questionHorselhjalpmedel();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionHorselhjalpmedel.questionHorselhjalpmedel();

    assertEquals(new ElementMapping(new ElementId("9"), null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfCodeIsGR23() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("1"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId(GR_II_III.code()))
                      .build()
              )
              .build()
      );

      final var element = QuestionHorselhjalpmedel.questionHorselhjalpmedel();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnTrueIfCodeIsTaxi() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("1"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId(TAXI.code()))
                      .build()
              )
              .build()
      );

      final var element = QuestionHorselhjalpmedel.questionHorselhjalpmedel();

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

      final var element = QuestionHorselhjalpmedel.questionHorselhjalpmedel();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementCodeIsNotGR23OrTaxi() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("1"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId("TEST"))
                      .build()
              )
              .build()
      );

      final var element = QuestionHorselhjalpmedel.questionHorselhjalpmedel();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}