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

class QuestionStrokeTest {

  private static final ElementId ELEMENT_ID = new ElementId("11.5");

  @Test
  void shallIncludeId() {
    final var element = QuestionStroke.questionStroke();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har personen haft stroke eller finns tecken på hjärnskada efter trauma, stroke "
            + "eller annan sjukdom i centrala nervsystemet?")
        .id(new FieldId("11.5"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionStroke.questionStroke();

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
                    "exists($11.5)"
                )
            )
            .build()
    );

    final var element = QuestionStroke.questionStroke();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionStroke.questionStroke();

    assertEquals(expectedValidations, element.validations());
  }
}