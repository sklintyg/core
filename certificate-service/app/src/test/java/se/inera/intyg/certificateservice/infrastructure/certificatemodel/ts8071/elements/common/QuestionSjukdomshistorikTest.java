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

class QuestionSjukdomshistorikTest {

  private static final ElementId ELEMENT_ID = new ElementId("7.3");

  @Test
  void shallIncludeId() {
    final var element = QuestionSjukdomshistorik.questionSjukdomshistorik();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Finns uppgift om annan sjukdomshistorik eller andra omständigheter som kan indikera påverkan på synfunktionerna?")
        .description(
            "Exempel på sjukdomshistorik och andra omständigheter som kan påverka synfunktionerna är stroke och laserbehandling av retinopati. Det "
                + "kan också vara skalltrauma, hjärntumör eller prematur födsel som är av sådan grad att den kan ha påverkan på synfältet.")
        .id(new FieldId("7.3"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionSjukdomshistorik.questionSjukdomshistorik();

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
                    "exists($7.3)"
                )
            ).build(),
        ElementRuleExpression.builder()
            .id(new ElementId("4"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("!$4.1 && !empty($4.1)"))
            .build()
    );

    final var element = QuestionSjukdomshistorik.questionSjukdomshistorik();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionSjukdomshistorik.questionSjukdomshistorik();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnFalseIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("4"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionSjukdomshistorik.questionSjukdomshistorik();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
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

      final var element = QuestionSjukdomshistorik.questionSjukdomshistorik();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallIncludeMapping() {
      final var element = QuestionSjukdomshistorik.questionSjukdomshistorik();

      assertEquals(new ElementMapping(new ElementId("7"), null), element.mapping());
    }

    @Test
    void shallReturnTrueIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("4"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionSjukdomshistorik.questionSjukdomshistorik();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }
  }
}