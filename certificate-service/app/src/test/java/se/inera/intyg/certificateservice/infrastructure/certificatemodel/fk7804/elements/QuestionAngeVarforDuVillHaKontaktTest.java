package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAngeVarforDuVillHaKontakt.QUESTION_VARFOR_KONTAKT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAngeVarforDuVillHaKontakt.QUESTION_VARFOR_KONTAKT_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionAngeVarforDuVillHaKontaktTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionAngeVarforDuVillHaKontakt.questionAngeVarforDuVillHaKontakt();
    assertEquals(QUESTION_VARFOR_KONTAKT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(QUESTION_VARFOR_KONTAKT_FIELD_ID)
        .name("Jag önskar att Försäkringskassan kontaktar mig")
        .build();

    final var element = QuestionAngeVarforDuVillHaKontakt.questionAngeVarforDuVillHaKontakt();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionAngeVarforDuVillHaKontakt.questionAngeVarforDuVillHaKontakt();
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .type(ElementRuleType.MANDATORY)
            .id(QUESTION_VARFOR_KONTAKT_ID)
            .expression(new RuleExpression("$" + QUESTION_VARFOR_KONTAKT_FIELD_ID.value()))
            .build()
    );

    final var element = QuestionAngeVarforDuVillHaKontakt.questionAngeVarforDuVillHaKontakt();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionAngeVarforDuVillHaKontakt.questionAngeVarforDuVillHaKontakt();
    final var expectedMapping = new ElementMapping(QUESTION_VARFOR_KONTAKT_ID, null);
    assertEquals(expectedMapping, element.mapping());
  }
}