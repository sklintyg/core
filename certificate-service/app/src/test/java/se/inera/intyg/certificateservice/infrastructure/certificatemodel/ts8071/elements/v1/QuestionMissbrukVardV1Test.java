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

class QuestionMissbrukVardV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("18.6");

  @Test
  void shallIncludeId() {
    final var element = QuestionMissbrukVardV1.questionMissbrukVardV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har personen vid något tillfälle vårdats eller sökt hjälp för missbruk eller beroende av alkohol, narkotika eller läkemedel?")
        .description(
            "Här avses exempelvis stödinsatser som vård på behandlingshem, tvångsvård eller deltagande i substitutionsbehandlingsprogram såsom LARO. Beskriv vilken typ av insats det rör sig om.")
        .id(new FieldId("18.6"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionMissbrukVardV1.questionMissbrukVardV1();

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
                    "exists($18.6)"
                )
            )
            .build()
    );

    final var element = QuestionMissbrukVardV1.questionMissbrukVardV1();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionMissbrukVardV1.questionMissbrukVardV1();

    assertEquals(expectedValidations, element.validations());
  }


  @Test
  void shallIncludeMapping() {
    final var element = QuestionMissbrukVardV1.questionMissbrukVardV1();

    assertEquals(new ElementMapping(new ElementId("18"), null), element.mapping());
  }
}