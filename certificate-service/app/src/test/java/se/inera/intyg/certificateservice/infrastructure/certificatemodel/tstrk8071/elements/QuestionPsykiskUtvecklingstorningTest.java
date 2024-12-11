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

class QuestionPsykiskUtvecklingsstorningTest {

  private static final ElementId ELEMENT_ID = new ElementId("20.6");

  @Test
  void shallIncludeId() {
    final var element = QuestionPsykiskUtvecklingsstorning.questionPsykiskUtvecklingsstorning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har personen någon psykisk utvecklingsstörning?")
        .id(new FieldId("20.6"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionPsykiskUtvecklingsstorning.questionPsykiskUtvecklingsstorning();

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
                    "exists($20.6)"
                )
            )
            .build()
    );

    final var element = QuestionPsykiskUtvecklingsstorning.questionPsykiskUtvecklingsstorning();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionPsykiskUtvecklingsstorning.questionPsykiskUtvecklingsstorning();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionPsykiskUtvecklingsstorning.questionPsykiskUtvecklingsstorning();

    assertEquals(new ElementMapping(new ElementId("20"), null), element.mapping());
  }
}