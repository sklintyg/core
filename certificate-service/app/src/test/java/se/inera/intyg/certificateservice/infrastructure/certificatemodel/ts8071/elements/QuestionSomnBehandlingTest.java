package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

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

class QuestionSomnBehandlingTest {

  private static final ElementId ELEMENT_ID = new ElementId("17.3");

  @Test
  void shallIncludeId() {
    final var element = QuestionSomnBehandling.questionSomnBehandling();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Förekommer behandling mot sömn- och vakenhetsstörning?")
        .description(
            "Här avses behandling för sömnapné med så väl bettskena eller annat hjälpmedel för andning såsom exempelvis CPAP, BiPAP eller APAP. Här avses även läkemedel för narkolepsi eller narkotikaklassade läkemedel för annan sömn- eller vakenhetsstörning")
        .id(new FieldId("17.3"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionSomnBehandling.questionSomnBehandling();

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
                    "exists($17.3)"
                )
            )
            .build()
    );

    final var element = QuestionSomnBehandling.questionSomnBehandling();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionSomnBehandling.questionSomnBehandling();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionSomnBehandling.questionSomnBehandling();

    assertEquals(new ElementMapping(new ElementId("17"), null), element.mapping());
  }
}