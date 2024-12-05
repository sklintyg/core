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

class QuestionRorlighetHjalpaPassagerareTest {

  private static final ElementId ELEMENT_ID = new ElementId("10.3");

  @Test
  void shallIncludeId() {
    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har personen en nedsättning av rörelseförmågan som gör att personen inte kan "
                + "hjälpa passagerare in och ut ur fordonet samt med bilbälte?")
        .id(new FieldId("10.3"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

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
                    "exists($10.3)"
                )
            )
            .build()
    );

    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare();

    assertEquals(new ElementMapping(new ElementId("10"), null), element.mapping());
  }
}