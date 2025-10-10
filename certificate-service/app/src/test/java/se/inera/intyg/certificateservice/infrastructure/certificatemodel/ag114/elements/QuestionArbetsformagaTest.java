package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionArbetsformaga.QUESTION_ARBETSFORMAGA_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionArbetsformaga.QUESTION_ARBETSFORMAGA_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionArbetsformagaTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionArbetsformaga.questionArbetsformaga();
    assertEquals(QUESTION_ARBETSFORMAGA_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(QUESTION_ARBETSFORMAGA_FIELD_ID)
        .name("På vilket sätt medför sjukdomen nedsatt arbetsförmåga?")
        .description(
            """
                Uppgift om på vilket sätt arbetsförmågan är nedsatt ska anges oavsett om diagnos har angetts eller inte, detta för att arbetstagaren ska kunna styrka sin rätt till sjuklön och rätt att vara frånvarande från arbetet.
                
                Informationen har även betydelse för arbetsgivaren i rehabiliteringsarbetet.
                
                Uppgift om på vilket sätt sjukdomen medför nedsatt arbetsförmåga blir särskilt viktig om patienten valt att inte uppge diagnos.""")
        .build();

    final var element = QuestionArbetsformaga.questionArbetsformaga();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionArbetsformaga.questionArbetsformaga();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_ARBETSFORMAGA_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$5.1"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionArbetsformaga.questionArbetsformaga();
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }
}
