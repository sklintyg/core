package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

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

class QuestionDiabetesV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("12");

  @Test
  void shallIncludeId() {
    final var element = QuestionDiabetesV1.questionDiabetesV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har personen läkemedelsbehandlad diabetes?")
        .description(
            "Har personen läkemedelsbehandlad diabetes krävs ett läkarintyg gällande sjukdomen. Intyget går att skicka in digitalt via Webcert.")
        .id(new FieldId("12.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionDiabetesV1.questionDiabetesV1();

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
                    "exists($12.1)"
                )
            )
            .build()
    );

    final var element = QuestionDiabetesV1.questionDiabetesV1();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionDiabetesV1.questionDiabetesV1();

    assertEquals(expectedValidations, element.validations());
  }
}