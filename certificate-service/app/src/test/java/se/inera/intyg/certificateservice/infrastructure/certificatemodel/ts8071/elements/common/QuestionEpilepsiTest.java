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

class QuestionEpilepsiTest {

  private static final ElementId ELEMENT_ID = new ElementId("14");

  @Test
  void shallIncludeId() {
    final var element = QuestionEpilepsi.questionEpilepsi();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har eller har personen haft epilepsi?")
        .id(new FieldId("14.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionEpilepsi.questionEpilepsi();

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
                    "exists($14.1)"
                )
            )
            .build()
    );

    final var element = QuestionEpilepsi.questionEpilepsi();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionEpilepsi.questionEpilepsi();

    assertEquals(expectedValidations, element.validations());
  }
}