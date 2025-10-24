package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

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

class QuestionEpilepsiAnfallTest {

  private static final ElementId ELEMENT_ID = new ElementId("14.3");

  @Test
  void shallIncludeId() {
    final var element = QuestionEpilepsiAnfall.questionEpilepsiAnfall();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har eller har personen haft epileptiskt anfall (utan att diagnosen epilepsi har ställts)?")
        .id(new FieldId("14.3"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionEpilepsiAnfall.questionEpilepsiAnfall();

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
                    "exists($14.3)"
                )
            )
            .build()
    );

    final var element = QuestionEpilepsiAnfall.questionEpilepsiAnfall();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionEpilepsiAnfall.questionEpilepsiAnfall();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionEpilepsiAnfall.questionEpilepsiAnfall();

    assertEquals(new ElementMapping(new ElementId("14"), null), element.mapping());
  }
}
  