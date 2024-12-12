package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

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

class QuestionSomnTest {

  private static final ElementId ELEMENT_ID = new ElementId("17");

  @Test
  void shallIncludeId() {
    final var element = QuestionSomn.questionSomn();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har personen en sömn- eller vakenhetsstörning eller symtom på sådan problematik?")
        .description(
            "Här avses sömnapné och narkolepsi. Här avses även snarksjukdom som kan utgöra en trafiksäkerhetsrisk och annan sjukdom med sömnstörning. Insomningsbesvär som läkemedelbehandlas och inte utgör en trafiksäkerhetsrisk omfattas inte.")
        .id(new FieldId("17.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionSomn.questionSomn();

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
                    "exists($17.1)"
                )
            )
            .build()
    );

    final var element = QuestionSomn.questionSomn();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionSomn.questionSomn();

    assertEquals(expectedValidations, element.validations());
  }
}