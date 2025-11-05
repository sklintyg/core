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

class QuestionMissbrukVardV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("18.6");

  @Test
  void shouldIncludeId() {
    final var element = QuestionMissbrukVardV2.questionMissbrukVardV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .id(new FieldId("18.6"))
        .name(
            "Har personen vid något tillfälle vårdats eller sökt hjälp för substansrelaterad diagnos, överkonsumtion av alkohol eller regelbundet bruk av psykoaktiva substanser eller läkemedel?")
        .description(
            "Här avses uppgifter om eller tecken på beroende av psykoaktiv substans oavsett när i tid detta förekommit. Här avses också uppgifter om eller tecken på aktuellt skadligt mönster av bruk, skadligt bruk eller överkonsumtion av alkohol som inte är tillfälligt under de senaste tolv månaderna.")
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionMissbrukVardV2.questionMissbrukVardV2();

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
                    "exists($18.6)"
                )
            )
            .build()
    );

    final var element = QuestionMissbrukVardV2.questionMissbrukVardV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionMissbrukVardV2.questionMissbrukVardV2();

    assertEquals(expectedValidations, element.validations());
  }
}

