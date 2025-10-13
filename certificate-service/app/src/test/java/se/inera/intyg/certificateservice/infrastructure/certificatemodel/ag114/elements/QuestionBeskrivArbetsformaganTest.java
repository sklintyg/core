package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionBeskrivArbetsformagan.QUESTION_BESKRIV_ARBETSFORMAGAN_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionBeskrivArbetsformagan.QUESTION_BESKRIV_ARBETSFORMAGAN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFinnsArbetsformaga.QUESTION_FINNS_ARBETSFORMAGA_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionBeskrivArbetsformaganTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionBeskrivArbetsformagan.questionBeskrivArbetsformagan();
    assertEquals(QUESTION_BESKRIV_ARBETSFORMAGAN_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(QUESTION_BESKRIV_ARBETSFORMAGAN_FIELD_ID)
        .name("Beskriv arbetsförmågan")
        .description(
            "Svar på nedanstående frågor kan ge arbetsgivaren vägledning när det gäller eventuell anpassning av arbetsuppgifter, behov av arbetsresor eller möjlighet för arbetstagaren att hålla kontakten med arbetsplatsen.<ol><li>Vilka arbetsuppgifter kan arbetstagaren utföra trots sin nedsatta arbetsförmåga?</li><li>Vilka arbetsuppgifter och moment bör arbetstagaren inte alls utföra av medicinska skäl?</li><li>Kan t ex arbetsresor till arbetet hjälpa?</li><li>Är det möjligt för arbetstagaren att vistas på arbetsplatsen vid till exempel arbetsplatsträffar?</li></ol>")
        .build();

    final var element = QuestionBeskrivArbetsformagan.questionBeskrivArbetsformagan();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionBeskrivArbetsformagan.questionBeskrivArbetsformagan();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_FINNS_ARBETSFORMAGA_ID)
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$6.1"))
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_BESKRIV_ARBETSFORMAGAN_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$6.2"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionBeskrivArbetsformagan.questionBeskrivArbetsformagan();
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionBeskrivArbetsformagan.questionBeskrivArbetsformagan();
    final var expectedMapping = new ElementMapping(QUESTION_FINNS_ARBETSFORMAGA_ID, null);
    assertEquals(expectedMapping, element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfParentQuestionIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_FINNS_ARBETSFORMAGA_ID)
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionBeskrivArbetsformagan.questionBeskrivArbetsformagan();
      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfParentQuestionIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_FINNS_ARBETSFORMAGA_ID)
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionBeskrivArbetsformagan.questionBeskrivArbetsformagan();
      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfParentQuestionIsNull() {
      final var elementData = List.of(
          ElementData.builder()
              .id(QUESTION_FINNS_ARBETSFORMAGA_ID)
              .value(
                  ElementValueBoolean.builder()
                      .value(null)
                      .build()
              )
              .build()
      );

      final var element = QuestionBeskrivArbetsformagan.questionBeskrivArbetsformagan();
      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}
