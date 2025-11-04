package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

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

class QuestionMissbrukJournaluppgifterV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("18.3");

  @Test
  void shouldIncludeId() {
    final var element = QuestionMissbrukJournaluppgifterV2.questionMissbrukJournaluppgifterV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .id(new FieldId("18.3"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .name(
            "Finns journaluppgifter, anamnestiska uppgifter, resultat av laboratorieprover eller andra tecken på överkonsumtion av alkohol som inte är tillfällig eller tecken på pågående bruk av psykoaktiva substanser eller läkemedel?")
        .description(
            "Här avses uppgifter om eller tecken på beroende av psykoaktiv substans oavsett när i tid detta förekommit. Här avses också uppgifter om eller tecken på aktuellt skadligt mönster av bruk, skadligt bruk eller överkonsumtion av alkohol som inte är tillfälligt under de senaste tolv månaderna.")
        .build();

    final var element = QuestionMissbrukJournaluppgifterV2.questionMissbrukJournaluppgifterV2();

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
                    "exists($18.3)"
                )
            )
            .build()
    );

    final var element = QuestionMissbrukJournaluppgifterV2.questionMissbrukJournaluppgifterV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionMissbrukJournaluppgifterV2.questionMissbrukJournaluppgifterV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionMissbrukJournaluppgifterV2.questionMissbrukJournaluppgifterV2();

    assertEquals(new ElementMapping(new ElementId("18"), null), element.mapping());
  }
}

