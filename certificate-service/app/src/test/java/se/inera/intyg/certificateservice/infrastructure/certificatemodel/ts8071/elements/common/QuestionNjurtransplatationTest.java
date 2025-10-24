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

class QuestionNjurtransplatationTest {

  private static final ElementId ELEMENT_ID = new ElementId("15.2");

  @Test
  void shallIncludeId() {
    final var element = QuestionNjurtransplatation.questionNjurtransplatation();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har njurtransplantation genomgåtts?")
        .id(new FieldId("15.2"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionNjurtransplatation.questionNjurtransplatation();

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
                    "exists($15.2)"
                )
            )
            .build()
    );

    final var element = QuestionNjurtransplatation.questionNjurtransplatation();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionNjurtransplatation.questionNjurtransplatation();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionNjurtransplatation.questionNjurtransplatation();

    assertEquals(new ElementMapping(new ElementId("15"), null), element.mapping());
  }
}