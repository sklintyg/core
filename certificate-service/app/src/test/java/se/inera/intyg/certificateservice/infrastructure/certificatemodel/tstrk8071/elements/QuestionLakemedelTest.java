package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

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

class QuestionLakemedelTest {

  private static final ElementId ELEMENT_ID = new ElementId("18.8");

  @Test
  void shallIncludeId() {
    final var element = QuestionLakemedel.questionLakemedel();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Pågår läkarordinerat bruk av läkemedel som kan innebära en trafiksäkerhetsrisk?")
        .description(
            "Här avses pågående läkarordinerat bruk av främst psykoaktiva substanser men även substanser som inte är av psykoaktivt slag men som kan påverka förmågan att framföra fordon. Ledning hittas i 12 kap. Transportstyrelsens föreskrifter och allmänna råd (TSFS 2010:125) om medicinska krav för körkort m.m.")
        .id(new FieldId("18.8"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionLakemedel.questionLakemedel();

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
                    "exists($18.8)"
                )
            )
            .build()
    );

    final var element = QuestionLakemedel.questionLakemedel();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionLakemedel.questionLakemedel();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionLakemedel.questionLakemedel();

    assertEquals(new ElementMapping(new ElementId("18"), null), element.mapping());
  }
}