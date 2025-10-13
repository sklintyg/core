package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFormedlaInfoOmDiagnosTillAG.FORMEDLA_DIAGNOSIS_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFormedlaInfoOmDiagnosTillAG.QUESTION_FORMEDLA_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFormedlaInfoOmDiagnosTillAG.questionFormedlaInfoOmDiagnosTillAG;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.singleExpression;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenPdfConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleAutofill;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionFormedlaInfoOmDiagnosTillAGTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionFormedlaInfoOmDiagnosTillAG.questionFormedlaInfoOmDiagnosTillAG();
    assertEquals(QUESTION_FORMEDLA_DIAGNOS_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean
        .builder()
        .id(FORMEDLA_DIAGNOSIS_FIELD_ID)
        .selectedText("Ja")
        .unselectedText("Nej")
        .name(
            "Önskar patienten förmedla information om diagnos/diagnoser till sin arbetsgivare?")
        .description(
            "Information om diagnos kan vara viktig för patientens arbetsgivare. Det kan underlätta anpassning av patientens arbetssituation. Det kan också göra att patienten snabbare kommer tillbaka till arbetet.")
        .build();

    final var element = QuestionFormedlaInfoOmDiagnosTillAG.questionFormedlaInfoOmDiagnosTillAG();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionFormedlaInfoOmDiagnosTillAG.questionFormedlaInfoOmDiagnosTillAG();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_FORMEDLA_DIAGNOS_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("exists($100.1)"))
            .build(),
        ElementRuleAutofill.builder()
            .id(QUESTION_SMITTBARARPENNING_ID)
            .type(ElementRuleType.AUTO_FILL)
            .expression(
                new RuleExpression(singleExpression(QUESTION_SMITTBARARPENNING_FIELD_ID.value())))
            .fillValue(ElementValueBoolean.builder()
                .booleanId(FORMEDLA_DIAGNOSIS_FIELD_ID)
                .value(true)
                .build()
            ).build(),
        ElementRuleExpression.builder()
            .id(QUESTION_SMITTBARARPENNING_ID)
            .type(ElementRuleType.DISABLE)
            .expression(
                new RuleExpression(
                    singleExpression(QUESTION_SMITTBARARPENNING_FIELD_ID.value())
                )
            )
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = questionFormedlaInfoOmDiagnosTillAG();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldReturnPdfConfiguration() {
    final var response = QuestionFormedlaInfoOmDiagnosTillAG.questionFormedlaInfoOmDiagnosTillAG();

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