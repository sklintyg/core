package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionKognitivStorningV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("16");

  @Test
  void shouldIncludeId() {
    final var element = QuestionKognitivStorningV2.questionKognitivStorningV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .id(new FieldId("16.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .name("Har personen allvarlig kognitiv störning?")
        .build();

    final var element = QuestionKognitivStorningV2.questionKognitivStorningV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($16.1)"
                )
            )
            .build()
    );

    final var element = QuestionKognitivStorningV2.questionKognitivStorningV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionKognitivStorningV2.questionKognitivStorningV2();

    assertEquals(expectedValidations, element.validations());
  }
}

