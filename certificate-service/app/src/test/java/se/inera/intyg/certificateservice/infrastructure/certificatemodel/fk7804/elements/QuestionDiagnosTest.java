package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionDiagnos.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionDiagnos.DIAGNOS_2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionDiagnos.DIAGNOS_3;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionDiagnos.questionDiagnos;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDiagnoses;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;

class QuestionDiagnosTest {

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private static final ElementId ELEMENT_ID = new ElementId("6");

  @Test
  void shallIncludeId() {
    final var element = questionDiagnos(
        diagnosisCodeRepository);

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationDiagnosis.builder()
        .id(new FieldId("6.1"))
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

    final var element = questionDiagnos(
        diagnosisCodeRepository);

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($huvuddiagnos)"
                )
            )
            .build()
    );

    final var element = questionDiagnos(
        diagnosisCodeRepository);

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
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

    final var element = questionDiagnos(
        diagnosisCodeRepository);

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldHaveCorrectPdfConfiguration() {
    final var expected = PdfConfigurationDiagnoses.builder()
        .diagnoses(Map.of(
            DIAGNOS_1, PdfConfigurationDiagnosis.builder()
                .pdfNameFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtDiagnoser[0]"))
                .pdfCodeFieldIds(List.of(
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod1[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod2[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod3[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod4[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod5[0]")
                ))
                .build(),
            DIAGNOS_2, PdfConfigurationDiagnosis.builder()
                .pdfNameFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtDiagnoser2[0]"))
                .pdfCodeFieldIds(List.of(
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod6[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod7[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod8[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod9[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod10[0]")
                ))
                .build(),
            DIAGNOS_3, PdfConfigurationDiagnosis.builder()
                .pdfNameFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtDiagnoser3[0]"))
                .pdfCodeFieldIds(List.of(
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod11[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod12[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod13[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod14[0]"),
                    new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod15[0]")
                ))
                .build()
        )).build();
    assertEquals(expected, questionDiagnos(diagnosisCodeRepository).pdfConfiguration());
  }
}
