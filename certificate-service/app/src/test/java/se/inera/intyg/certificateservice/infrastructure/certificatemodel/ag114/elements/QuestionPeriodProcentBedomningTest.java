package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodProcentBedomning.QUESTION_PERIOD_PROCENT_BEDOMNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodProcentBedomning.QUESTION_PERIOD_PROCENT_BEDOMNING_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationInteger;

class QuestionPeriodProcentBedomningTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionPeriodProcentBedomning.questionPeriodProcentBedomning();
    assertEquals(QUESTION_PERIOD_PROCENT_BEDOMNING_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationInteger.builder()
        .id(QUESTION_PERIOD_PROCENT_BEDOMNING_FIELD_ID)
        .name("Patientens arbetsförmåga bedöms vara nedsatt med (ange antal procent)")
        .min(1)
        .max(100)
        .build();

    final var element = QuestionPeriodProcentBedomning.questionPeriodProcentBedomning();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionPeriodProcentBedomning.questionPeriodProcentBedomning();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_PERIOD_PROCENT_BEDOMNING_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$7.1"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionPeriodProcentBedomning.questionPeriodProcentBedomning();
    final var expectedValidations = List.of(
        ElementValidationInteger.builder()
            .mandatory(true)
            .min(1)
            .max(100)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }
}
