package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenPdfConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionFormedlaDiagnosTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionFormedlaDiagnos.questionFormedlaDiagnos();
    assertEquals(QUESTION_FORMEDLA_DIAGNOS_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .id(QUESTION_FORMEDLA_DIAGNOS_FIELD_ID)
        .name("Önskar patienten förmedla information om diagnos/diagnoser till sin arbetsgivare?")
        .description(
            "Information om diagnos kan vara viktig för patientens arbetsgivare. Det kan underlätta anpassning av patientens arbetssituation. Det kan också göra att patienten snabbare kommer tillbaka till arbetet.")
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionFormedlaDiagnos.questionFormedlaDiagnos();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionFormedlaDiagnos.questionFormedlaDiagnos();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_FORMEDLA_DIAGNOS_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("exists($3.1)"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionFormedlaDiagnos.questionFormedlaDiagnos();
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldReturnPdfConfiguration() {
    final var response = QuestionFormedlaDiagnos.questionFormedlaDiagnos();

    final var pdfConfig = (CitizenPdfConfiguration) response.pdfConfiguration();

    assertAll(
        () -> assertEquals(QUESTION_DIAGNOS_ID, pdfConfig.hiddenBy()),
        () -> assertNotNull(pdfConfig.shouldHide()),
        () -> assertEquals(
            ElementSimplifiedValueText.builder()
                .text("På patientens begäran uppges inte diagnos")
                .build(), pdfConfig.replacementValue()
        )
    );
  }
}
