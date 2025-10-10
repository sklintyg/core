package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionSysselsattningTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionSysselsattning.questionSysselsattning();
    assertEquals(QUESTION_SYSSELSATTNING_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(QUESTION_SYSSELSATTNING_FIELD_ID)
        .name("Bedöm arbetsförmåga utifrån ordinarie arbete. Ange yrke och arbetsuppgifter.")
        .build();

    final var element = QuestionSysselsattning.questionSysselsattning();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionSysselsattning.questionSysselsattning();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_SYSSELSATTNING_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$1.1"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionSysselsattning.questionSysselsattning();
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }
}
