package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAntalManader.QUESTION_ANTAL_MANADER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAntalManader.QUESTION_ANTAL_MANADER_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationInteger;

class QuestionAntalManaderTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionAntalManader.questionAntalManader();
    assertEquals(QUESTION_ANTAL_MANADER_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationInteger.builder()
        .id(QUESTION_ANTAL_MANADER_FIELD_ID)
        .name("Ange antal månader")
        .build();

    final var element = QuestionAntalManader.questionAntalManader();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionAntalManader.questionAntalManader();
    final var expectedValidations = List.of(
        ElementValidationInteger.builder()
            .mandatory(true)
            .min(1)
            .max(99)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .type(ElementRuleType.MANDATORY)
            .id(QUESTION_ANTAL_MANADER_ID)
            .expression(new RuleExpression("$" + QUESTION_ANTAL_MANADER_ID.id()))
            .build()
    );

    final var element = QuestionAntalManader.questionAntalManader();

    assertEquals(expectedRules, element.rules());
  }
}