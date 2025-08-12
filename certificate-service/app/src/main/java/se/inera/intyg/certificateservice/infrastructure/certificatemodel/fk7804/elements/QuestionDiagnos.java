package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import java.util.Map;
import se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDiagnoses;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;

public class QuestionDiagnos {

  public static final ElementId QUESTION_DIAGNOS_ID = new ElementId("6");
  private static final FieldId DIAGNOSIS_FIELD_ID = new FieldId("6.1");
  public static final FieldId DIAGNOS_1 = new FieldId("huvuddiagnos");
  public static final FieldId DIAGNOS_2 = new FieldId("diagnos2");
  public static final FieldId DIAGNOS_3 = new FieldId("diagnos3");

  private QuestionDiagnos() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDiagnos(
      DiagnosisCodeRepository diagnosisCodeRepository) {
    return ElementSpecification.builder()
        .id(QUESTION_DIAGNOS_ID)
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .id(DIAGNOSIS_FIELD_ID)
                .name("Diagnos/diagnoser för sjukdom som orsakar nedsatt arbetsförmåga")
                .description(
                    "Ange vilken eller vilka sjukdomar som orsakar nedsatt arbetsförmåga. Den sjukdom som påverkar arbetsförmågan mest anges först. Diagnoskoden anges alltid med så många positioner som möjligt.")
                .terminology(List.of(CodeSystemIcd10Se.terminology()))
                .list(List.of(
                    new ElementDiagnosisListItem(DIAGNOS_1),
                    new ElementDiagnosisListItem(DIAGNOS_2),
                    new ElementDiagnosisListItem(DIAGNOS_3)
                ))
                .build()
        )
        .rules(List.of(
            CertificateElementRuleFactory.mandatoryExist(QUESTION_DIAGNOS_ID, DIAGNOS_1)
        ))
        .validations(List.of(
            ElementValidationDiagnosis.builder()
                .mandatoryField(DIAGNOS_1)
                .order(List.of(DIAGNOS_1, DIAGNOS_2, DIAGNOS_3))
                .diagnosisCodeRepository(diagnosisCodeRepository)
                .build()
        ))
        .pdfConfiguration(PdfConfigurationDiagnoses.builder()
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
            ))
            .build())
        .mapping(new ElementMapping(CustomMapperId.UNIFIED_DIAGNOSIS_LIST))
        .build();
  }
}
