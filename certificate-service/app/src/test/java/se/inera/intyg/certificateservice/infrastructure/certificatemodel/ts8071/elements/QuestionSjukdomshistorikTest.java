package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

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

class QuestionSjukdomshistorikTest {

  private static final ElementId ELEMENT_ID = new ElementId("8");

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
        .id(new FieldId("8.1"))
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
                    "exists($8.1)"
                )
            )
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
}