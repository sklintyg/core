package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodBedomning.QUESTION_PERIOD_BEDOMNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodBedomning.QUESTION_PERIOD_BEDOMNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodProcentBedomning.QUESTION_PERIOD_PROCENT_BEDOMNING_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRange;

class QuestionPeriodBedomningTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionPeriodBedomning.questionPeriodBedomning();
    assertEquals(QUESTION_PERIOD_BEDOMNING_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationDateRange.builder()
        .id(QUESTION_PERIOD_BEDOMNING_FIELD_ID)
        .name("Period då arbetsförmågan bedöms vara nedsatt")
        .labelFrom("Fr.o.m")
        .labelTo("T.o.m")
        .build();

    final var element = QuestionPeriodBedomning.questionPeriodBedomning();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionPeriodBedomning.questionPeriodBedomning();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_PERIOD_BEDOMNING_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$7.2"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionPeriodBedomning.questionPeriodBedomning();
    final var expectedValidations = List.of(
        ElementValidationDateRange.builder()
            .mandatory(true)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionPeriodBedomning.questionPeriodBedomning();
    final var expectedMapping = new ElementMapping(QUESTION_PERIOD_PROCENT_BEDOMNING_ID, null);
    assertEquals(expectedMapping, element.mapping());
  }
}
