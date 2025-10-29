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

class QuestionPsykiskV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("19");

  @Test
  void shallIncludeId() {
    final var element = QuestionPsykiskV1.questionPsykiskV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har personen eller har personen haft psykisk sjukdom eller störning, till exempel schizofreni, annan psykos eller bipolär (manodepressiv) sjukdom?")
        .description(
            "Här avses sjukdomar och störningar som kan påverka beteendet, så att det kan utgöra en trafiksäkerhetsrisk. Med sjukdomar avses exempelvis schizofreni, annan psykos eller affektiva syndrom såsom bipolär sjukdom. Med störningar avses exempelvis olika personlighetsstörningar såsom paranoid, antisocial, narcissistisk eller emotionellt instabil personlighetsstörning och schizotyp personlighetsstörning. \n\n I normalfallet medför paniksyndrom, utmattningssyndrom, ångest (PTSD), generaliserat ångestsyndrom (GAD), årstidsbundna depressioner inte en trafiksäkerhetsrisk och behöver i sådant fall inte anges.")
        .id(new FieldId("19.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionPsykiskV1.questionPsykiskV1();

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
                    "exists($19.1)"
                )
            )
            .build()
    );

    final var element = QuestionPsykiskV1.questionPsykiskV1();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionPsykiskV1.questionPsykiskV1();

    assertEquals(expectedValidations, element.validations());
  }
}