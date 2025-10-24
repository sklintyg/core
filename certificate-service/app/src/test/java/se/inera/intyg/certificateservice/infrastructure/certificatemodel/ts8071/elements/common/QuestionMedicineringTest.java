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

class QuestionMedicineringTest {

  private static final ElementId ELEMENT_ID = new ElementId("21");

  @Test
  void shallIncludeId() {
    final var element = QuestionMedicinering.questionMedicinering();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har personen någon stadigvarande medicinering som inte nämnts i något avsnitt ovan?")
        .description(
            "Du behöver inte ange p-piller, vitamintillskott eller behandling mot hudsjukdomar. Inte heller medicin mot allergi, astma, mage/tarm exempelvis magkatarr, gikt, KOL, förstorad prostata, impotens eller nedsatt funktion i sköldkörteln.")
        .id(new FieldId("21.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionMedicinering.questionMedicinering();

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
                    "exists($21.1)"
                )
            )
            .build()
    );

    final var element = QuestionMedicinering.questionMedicinering();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionMedicinering.questionMedicinering();

    assertEquals(expectedValidations, element.validations());
  }
}