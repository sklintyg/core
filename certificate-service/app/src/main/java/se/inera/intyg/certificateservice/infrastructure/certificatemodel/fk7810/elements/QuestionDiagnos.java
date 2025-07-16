package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import java.util.Map;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
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

  public static final ElementId DIAGNOSIS_ID = new ElementId("58");
  private static final FieldId DIAGNOSIS_FIELD_ID = new FieldId("58.1");
  public static final FieldId DIAGNOS_1 = new FieldId("huvuddiagnos");
  public static final FieldId DIAGNOS_2 = new FieldId("diagnos2");
  public static final FieldId DIAGNOS_3 = new FieldId("diagnos3");
  public static final FieldId DIAGNOS_4 = new FieldId("diagnos4");
  public static final FieldId DIAGNOS_5 = new FieldId("diagnos5");
  private static final short DIAGNOSIS_CODE_LIMIT = (short) 81;

  private static final PdfFieldId PDF_DIAGNOSIS_ID_1 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiagnos[0]");
  private static final PdfFieldId PDF_CODE_ID_1_1 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod1[0]");
  private static final PdfFieldId PDF_CODE_ID_1_2 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod2[0]");
  private static final PdfFieldId PDF_CODE_ID_1_3 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod3[0]");
  private static final PdfFieldId PDF_CODE_ID_1_4 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod4[0]");
  private static final PdfFieldId PDF_CODE_ID_1_5 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod5[0]");

  private static final PdfFieldId PDF_DIAGNOSIS_ID_2 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiagnos2[0]");
  private static final PdfFieldId PDF_CODE_ID_2_1 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod6[0]");
  private static final PdfFieldId PDF_CODE_ID_2_2 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod7[0]");
  private static final PdfFieldId PDF_CODE_ID_2_3 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod8[0]");
  private static final PdfFieldId PDF_CODE_ID_2_4 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod9[0]");
  private static final PdfFieldId PDF_CODE_ID_2_5 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod10[0]");

  private static final PdfFieldId PDF_DIAGNOSIS_ID_3 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiagnos3[0]");
  private static final PdfFieldId PDF_CODE_ID_3_1 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod11[0]");
  private static final PdfFieldId PDF_CODE_ID_3_2 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod12[0]");
  private static final PdfFieldId PDF_CODE_ID_3_3 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod13[0]");
  private static final PdfFieldId PDF_CODE_ID_3_4 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod14[0]");
  private static final PdfFieldId PDF_CODE_ID_3_5 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod15[0]");

  private static final PdfFieldId PDF_DIAGNOSIS_ID_4 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiagnos4[0]");
  private static final PdfFieldId PDF_CODE_ID_4_1 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod16[0]");
  private static final PdfFieldId PDF_CODE_ID_4_2 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod17[0]");
  private static final PdfFieldId PDF_CODE_ID_4_3 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod18[0]");
  private static final PdfFieldId PDF_CODE_ID_4_4 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod19[0]");
  private static final PdfFieldId PDF_CODE_ID_4_5 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod20[0]");

  private static final PdfFieldId PDF_DIAGNOSIS_ID_5 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiagnos5[0]");
  private static final PdfFieldId PDF_CODE_ID_5_1 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod21[0]");
  private static final PdfFieldId PDF_CODE_ID_5_2 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod22[0]");
  private static final PdfFieldId PDF_CODE_ID_5_3 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod23[0]");
  private static final PdfFieldId PDF_CODE_ID_5_4 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod24[0]");
  private static final PdfFieldId PDF_CODE_ID_5_5 = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtDiaKod25[0]");

  private QuestionDiagnos() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDiagnos(
      DiagnosisCodeRepository diagnosisCodeRepository) {
    return ElementSpecification.builder()
        .id(DIAGNOSIS_ID)
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .id(DIAGNOSIS_FIELD_ID)
                .name(
                    "Diagnos eller diagnoser")
                .description(
                    "Ange diagnoskod med så många positioner som möjligt. Använd inga andra tecken än bokstäver och siffror.")
                .terminology(
                    List.of(
                        CodeSystemIcd10Se.terminology()
                    )
                )
                .list(
                    List.of(
                        new ElementDiagnosisListItem(DIAGNOS_1),
                        new ElementDiagnosisListItem(DIAGNOS_2),
                        new ElementDiagnosisListItem(DIAGNOS_3),
                        new ElementDiagnosisListItem(DIAGNOS_4),
                        new ElementDiagnosisListItem(DIAGNOS_5)
                    )
                )
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(DIAGNOSIS_ID,
                    DIAGNOS_1),
                CertificateElementRuleFactory.limit(DIAGNOSIS_ID, DIAGNOSIS_CODE_LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationDiagnosis.builder()
                    .mandatoryField(DIAGNOS_1)
                    .order(
                        List.of(DIAGNOS_1, DIAGNOS_2, DIAGNOS_3, DIAGNOS_4, DIAGNOS_5)
                    )
                    .diagnosisCodeRepository(diagnosisCodeRepository)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationDiagnoses.builder()
                .maxLength(82)
                .appearance("/ArialMT 9.00 Tf 0 g")
                .diagnoses(
                    Map.of(
                        DIAGNOS_1,
                        PdfConfigurationDiagnosis.builder()
                            .pdfNameFieldId(PDF_DIAGNOSIS_ID_1)
                            .pdfCodeFieldIds(
                                List.of(
                                    PDF_CODE_ID_1_1, PDF_CODE_ID_1_2, PDF_CODE_ID_1_3,
                                    PDF_CODE_ID_1_4, PDF_CODE_ID_1_5
                                )
                            )
                            .build(),
                        DIAGNOS_2,
                        PdfConfigurationDiagnosis.builder()
                            .pdfNameFieldId(PDF_DIAGNOSIS_ID_2)
                            .pdfCodeFieldIds(
                                List.of(
                                    PDF_CODE_ID_2_1, PDF_CODE_ID_2_2, PDF_CODE_ID_2_3,
                                    PDF_CODE_ID_2_4, PDF_CODE_ID_2_5
                                )
                            )
                            .build(),
                        DIAGNOS_3,
                        PdfConfigurationDiagnosis.builder()
                            .pdfNameFieldId(PDF_DIAGNOSIS_ID_3)
                            .pdfCodeFieldIds(
                                List.of(
                                    PDF_CODE_ID_3_1, PDF_CODE_ID_3_2, PDF_CODE_ID_3_3,
                                    PDF_CODE_ID_3_4, PDF_CODE_ID_3_5
                                )
                            )
                            .build(),
                        DIAGNOS_4,
                        PdfConfigurationDiagnosis.builder()
                            .pdfNameFieldId(PDF_DIAGNOSIS_ID_4)
                            .pdfCodeFieldIds(
                                List.of(
                                    PDF_CODE_ID_4_1, PDF_CODE_ID_4_2, PDF_CODE_ID_4_3,
                                    PDF_CODE_ID_4_4, PDF_CODE_ID_4_5
                                )
                            )
                            .build(),
                        DIAGNOS_5,
                        PdfConfigurationDiagnosis.builder()
                            .pdfNameFieldId(PDF_DIAGNOSIS_ID_5)
                            .pdfCodeFieldIds(
                                List.of(
                                    PDF_CODE_ID_5_1, PDF_CODE_ID_5_2, PDF_CODE_ID_5_3,
                                    PDF_CODE_ID_5_4, PDF_CODE_ID_5_5
                                )
                            )
                            .build()
                    )
                )
                .build()
        )
        .build();
  }
}