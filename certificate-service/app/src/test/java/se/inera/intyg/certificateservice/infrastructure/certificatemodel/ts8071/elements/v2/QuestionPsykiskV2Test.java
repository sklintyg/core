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

class QuestionPsykiskV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("19");

  @Test
  void shouldIncludeId() {
    final var element = QuestionPsykiskV2.questionPsykiskV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har personen eller har personen haft psykisk sjukdom eller störning?")
        .description(
            "Här avses sjukdomar och störningar som kan påverka beteendet, så att det kan utgöra en trafiksäkerhetsrisk. Med sjukdomar avses exempelvis schizofreni, annan psykos eller affektiva syndrom såsom bipolär sjukdom. Med störningar avses exempelvis olika personlighetsstörningar såsom paranoid, antisocial, narcissistisk eller emotionellt instabil personlighetsstörning och schizotyp personlighetsstörning.\n\n"
                + "I normalfallet medför paniksyndrom, utmattningssyndrom, ångest (PTSD), generaliserat ångestsyndrom (GAD), årstidsbundna depressioner inte en trafiksäkerhetsrisk och behöver i sådant fall inte anges.")
        .id(new FieldId("19.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionPsykiskV2.questionPsykiskV2();

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
                    "exists($19.1)"
                )
            )
            .build()
    );

    final var element = QuestionPsykiskV2.questionPsykiskV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionPsykiskV2.questionPsykiskV2();

    assertEquals(expectedValidations, element.validations());
  }
}


