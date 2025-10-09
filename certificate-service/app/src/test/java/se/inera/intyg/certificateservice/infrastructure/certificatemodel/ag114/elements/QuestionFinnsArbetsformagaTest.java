package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFinnsArbetsformaga.QUESTION_FINNS_ARBETSFORMAGA_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFinnsArbetsformaga.QUESTION_FINNS_ARBETSFORMAGA_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionFinnsArbetsformagaTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionFinnsArbetsformaga.questionFinnsArbetsformaga();
    assertEquals(QUESTION_FINNS_ARBETSFORMAGA_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .id(QUESTION_FINNS_ARBETSFORMAGA_FIELD_ID)
        .name("Finns arbetsförmåga trots sjukdom?")
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionFinnsArbetsformaga.questionFinnsArbetsformaga();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionFinnsArbetsformaga.questionFinnsArbetsformaga();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_FINNS_ARBETSFORMAGA_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("exists($6.1)"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionFinnsArbetsformaga.questionFinnsArbetsformaga();
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }
}
