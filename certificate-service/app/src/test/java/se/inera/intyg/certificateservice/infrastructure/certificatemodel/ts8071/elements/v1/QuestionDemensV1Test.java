package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionDemensV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("16.2");

  @Test
  void shallIncludeId() {
    final var element = QuestionDemensV1.questionDemensV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har personen diagnos demens eller annan kognitiv störning eller finns tecken på demens eller andra kognitiva störningar?")
        .description(
            "Med demens avses diagnos ställd utifrån vedertagen praxis eller utifrån de kriterier som anges i DSM-IV, DSM-V eller ICD-10. Med kognitiv störning avses kognitiv störning/svikt som inte är demens.")
        .id(new FieldId("16.2"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionDemensV1.questionDemensV1();

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
                    "exists($16.2)"
                )
            )
            .build()
    );

    final var element = QuestionDemensV1.questionDemensV1();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionDemensV1.questionDemensV1();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionDemensV1.questionDemensV1();

    assertEquals(new ElementMapping(new ElementId("16"), null), element.mapping());
  }
}