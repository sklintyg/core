package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

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

class QuestionNjurfunktionTest {

  private static final ElementId ELEMENT_ID = new ElementId("15");

  @Test
  void shallIncludeId() {
    final var element = QuestionNjurfunktion.questionNjurfunktion();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har personen allvarligt nedsatt njurfunktion?")
        .description(
            "Här avses inte tillstånd med bara lätt eller måttligt nedsatt njurfunktion som inte innebär någon trafiksäkerhetsrisk.")
        .id(new FieldId("15.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionNjurfunktion.questionNjurfunktion();

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
                    "exists($15.1)"
                )
            )
            .build()
    );

    final var element = QuestionNjurfunktion.questionNjurfunktion();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionNjurfunktion.questionNjurfunktion();

    assertEquals(expectedValidations, element.validations());
  }
}