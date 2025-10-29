package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenPdfConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;

class QuestionDiagnosTest {

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private static final ElementId ELEMENT_ID = new ElementId("4");

  @Test
  void shouldIncludeId() {
    final var element = QuestionDiagnos.questionDiagnos(
        diagnosisCodeRepository);

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationDiagnosis.builder()
        .id(new FieldId("4.1"))
        .name("Diagnos/diagnoser för sjukdom som orsakar nedsatt arbetsförmåga")
        .description(
            "Ange vilken eller vilka sjukdomar som orsakar nedsatt arbetsförmåga. Den sjukdom som påverkar arbetsförmågan mest anges först. Diagnoskoden anges alltid med så många positioner som möjligt.")
        .terminology(
            List.of(
                new ElementDiagnosisTerminology("ICD_10_SE", "ICD-10-SE", "1.2.752.116.1.1.1",
                    List.of("1.2.752.116.1.1.1.1.8", "1.2.752.116.1.1.1.1.3"))
            )
        )
        .list(
            List.of(
                new ElementDiagnosisListItem(new FieldId("huvuddiagnos")),
                new ElementDiagnosisListItem(new FieldId("diagnos2")),
                new ElementDiagnosisListItem(new FieldId("diagnos3"))
            )
        )
        .build();

    final var element = QuestionDiagnos.questionDiagnos(
        diagnosisCodeRepository);

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($huvuddiagnos)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .type(ElementRuleType.SHOW)
            .id(QUESTION_FORMEDLA_DIAGNOS_ID)
            .expression(
                new RuleExpression("$%s".formatted(QUESTION_FORMEDLA_DIAGNOS_FIELD_ID.value())))
            .build(),
        ElementRuleExpression.builder()
            .type(ElementRuleType.SHOW)
            .id(QUESTION_FORMEDLA_DIAGNOS_ID)
            .expression(
                new RuleExpression(
                    "empty($%s)".formatted(QUESTION_FORMEDLA_DIAGNOS_FIELD_ID.value())
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_FORMEDLA_DIAGNOS_ID)
            .type(ElementRuleType.DISABLE)
            .expression(
                new RuleExpression(
                    "empty($%s)".formatted(QUESTION_FORMEDLA_DIAGNOS_FIELD_ID.value())
                )
            )
            .build()
    );

    final var element = QuestionDiagnos.questionDiagnos(
        diagnosisCodeRepository);

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDiagnosis.builder()
            .mandatoryField(new FieldId("huvuddiagnos"))
            .order(
                List.of(
                    new FieldId("huvuddiagnos"),
                    new FieldId("diagnos2"),
                    new FieldId("diagnos3")
                )
            )
            .diagnosisCodeRepository(diagnosisCodeRepository)
            .build()
    );

    final var element = QuestionDiagnos.questionDiagnos(
        diagnosisCodeRepository);

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      final var element = QuestionDiagnos.questionDiagnos(
          diagnosisCodeRepository);
      final var shouldValidate = element.shouldValidate();
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId(QUESTION_FORMEDLA_DIAGNOS_ID.id()))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()
      );
      final var element = QuestionDiagnos.questionDiagnos(
          diagnosisCodeRepository);
      final var shouldValidate = element.shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementIsMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("999999"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      final var element = QuestionDiagnos.questionDiagnos(
          diagnosisCodeRepository);
      final var shouldValidate = element.shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shouldReturnPdfConfiguration() {
    final var response = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

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
