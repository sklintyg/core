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

class QuestionLakemedelV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("18.8");

  @Test
  void shouldIncludeId() {
    final var element = QuestionLakemedelV2.questionLakemedelV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Pågår läkarordinerat bruk av läkemedel som kan innebära en trafiksäkerhetsrisk?")
        .description(
            "Här avses pågående läkarordinerat bruk av främst psykoaktiva substanser men även substanser som inte är av psykoaktivt slag men som kan påverka förmågan att framföra fordon. Ledning hittas i 12 kap. Transportstyrelsens föreskrifter och allmänna råd (TSFS 2010:125) om medicinska krav för körkort m.m.")
        .id(new FieldId("18.8"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionLakemedelV2.questionLakemedelV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("exists($18.8)"))
            .build()
    );

    final var element = QuestionLakemedelV2.questionLakemedelV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionLakemedelV2.questionLakemedelV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionLakemedelV2.questionLakemedelV2();

    assertEquals(new ElementMapping(new ElementId("18"), null), element.mapping());
  }
}

