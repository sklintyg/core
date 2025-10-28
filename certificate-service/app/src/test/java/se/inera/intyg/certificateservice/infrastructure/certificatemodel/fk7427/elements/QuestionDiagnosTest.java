package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDiagnoses;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;

@ExtendWith(MockitoExtension.class)
class QuestionDiagnosTest {

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private static final ElementId ELEMENT_ID = new ElementId("58");

  @Test
  void shallIncludeId() {
    final var element = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationDiagnosis.builder()
        .id(new FieldId("58.1"))
        .name(
            "Barnets diagnos")
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

    final var element = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(
                new RuleLimit((short) 81)
            )
            .build()
    );

    final var element = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDiagnosis.builder()
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

    final var element = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expectedPdfConfiguration = PdfConfigurationDiagnoses.builder()
        .maxLength(45)
        .appearance("/ArialMT 9.00 Tf 0 g")
        .diagnoses(
            Map.of(
                new FieldId("huvuddiagnos"),
                PdfConfigurationDiagnosis.builder()
                    .pdfNameFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_txtAngeFunktionsnedsattning[0]"))
                    .pdfCodeFieldIds(
                        List.of(
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod1[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod2[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod3[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod4[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod5[0]")
                        )
                    )
                    .build(),
                new FieldId("diagnos2"),
                PdfConfigurationDiagnosis.builder()
                    .pdfNameFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_txtAngeFunktionsnedsattning2[0]"))
                    .pdfCodeFieldIds(
                        List.of(
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod6[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod7[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod8[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod9[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod10[0]")
                        )
                    )
                    .build(),
                new FieldId("diagnos3"),
                PdfConfigurationDiagnosis.builder()
                    .pdfNameFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_txtAngeFunktionsnedsattning3[0]"))
                    .pdfCodeFieldIds(
                        List.of(
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod11[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod12[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod13[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod14[0]"),
                            new PdfFieldId("form1[0].#subform[0].flt_txtDiaKod15[0]")
                        )
                    )
                    .build()
            )
        )
        .build();

    final var element = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

    assertEquals(element.pdfConfiguration(), expectedPdfConfiguration);
  }

}
