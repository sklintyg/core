package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.ARBETSTERAPEUT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.DIETIST;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.FYSIOTERAPEUT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.HABILITERING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.HORSELHABILITERING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.LOGOPED;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.NEUROPSYKIATRISKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.OVRIGT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.PSYKOLOG;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.SPECIALISTKLINIK;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.SYNHABILITERING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.VARDCENTRAL;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.VARD_UTOMLANDS;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID;

import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationMedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationMedicalInvestigationList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionUtredningEllerUnderlag {

  public static final ElementId QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID = new ElementId("4");
  private static final FieldId QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_FIELD_ID = new FieldId("4.1");
  public static final FieldId MEDICAL_INVESTIGATION_FIELD_ID_1 = new FieldId(
      "medicalInvestigation1");
  public static final FieldId MEDICAL_INVESTIGATION_FIELD_ID_2 = new FieldId(
      "medicalInvestigation2");
  public static final FieldId MEDICAL_INVESTIGATION_FIELD_ID_3 = new FieldId(
      "medicalInvestigation3");

  public static final String PDF_DATE_ID_1 = "form1[0].#subform[0].flt_datUtredningUnderlag_2[0]";
  public static final PdfFieldId PDF_SOURCE_ID_1 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtUtredning_1[0]");
  public static final String PDF_DATE_ID_2 = "form1[0].#subform[0].flt_datUtredningUnderlag_3[0]";
  public static final PdfFieldId PDF_SOURCE_ID_2 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtUtredning_2[0]");
  public static final String PDF_DATE_ID_3 = "form1[0].#subform[0].flt_datUtredningUnderlag_4[0]";
  public static final PdfFieldId PDF_SOURCE_ID_3 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtUtredning_3[0]");
  public static final PdfFieldId INVESTIGATION_PDF_FIELD_ID_1 = new PdfFieldId(
      "form1[0].#subform[0].lbx_listVardeUtredningUnderlag_1[0]");
  public static final PdfFieldId INVESTIGATION_PDF_FIELD_ID_2 = new PdfFieldId(
      "form1[0].#subform[0].lbx_listVardeUtredningUnderlag_2[0]");
  public static final PdfFieldId INVESTIGATION_PDF_FIELD_ID_3 = new PdfFieldId(
      "form1[0].#subform[0].lbx_listVardeUtredningUnderlag_3[0]");
  public static final int LIMIT = 53;

  private QuestionUtredningEllerUnderlag() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionUtredningEllerUnderlag(
      ElementSpecification... children) {
    final var medicalInvestigations = List.of(
        getMedicalInvestigationConfig(MEDICAL_INVESTIGATION_FIELD_ID_1),
        getMedicalInvestigationConfig(MEDICAL_INVESTIGATION_FIELD_ID_2),
        getMedicalInvestigationConfig(MEDICAL_INVESTIGATION_FIELD_ID_3)
    );

    return ElementSpecification.builder()
        .id(QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID)
        .configuration(
            ElementConfigurationMedicalInvestigationList.builder()
                .id(QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_FIELD_ID)
                .name("Ange utredning eller underlag")
                .informationSourceDescription(
                    "Skriv exempelvis Neuropsykiatriska kliniken på X-stads sjukhus eller om patienten själv kommer att bifoga utredningen till sin ansökan.")
                .dateText("Datum")
                .typeText("Utredning eller underlag")
                .informationSourceText("Från vilken vårdgivare")
                .list(medicalInvestigations)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryNotEmpty(
                    QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID,
                    List.of(
                        getDateId(MEDICAL_INVESTIGATION_FIELD_ID_1),
                        getInvestigationTypeId(MEDICAL_INVESTIGATION_FIELD_ID_1),
                        getInformationSourceId(MEDICAL_INVESTIGATION_FIELD_ID_1)
                    )
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID,
                    QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID,
                    (short) LIMIT
                )
            )
        )
        .validations(
            List.of(
                ElementValidationMedicalInvestigationList.builder()
                    .mandatory(true)
                    .max(Period.ofDays(0))
                    .limit(LIMIT)
                    .build()
            )
        )
        .shouldValidate(ShouldValidateFactory.valueBoolean(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID))
        .pdfConfiguration(
            PdfConfigurationMedicalInvestigationList.builder()
                .list(
                    Map.of(
                        MEDICAL_INVESTIGATION_FIELD_ID_1,
                        PdfConfigurationMedicalInvestigation.builder()
                            .datePdfFieldId(new PdfFieldId(PDF_DATE_ID_1))
                            .sourceTypePdfFieldId(PDF_SOURCE_ID_1)
                            .investigationPdfFieldId(INVESTIGATION_PDF_FIELD_ID_1)
                            .investigationPdfOptions(getInvestigationPdfOptions())
                            .build(),
                        MEDICAL_INVESTIGATION_FIELD_ID_2,
                        PdfConfigurationMedicalInvestigation.builder()
                            .datePdfFieldId(new PdfFieldId(PDF_DATE_ID_2))
                            .sourceTypePdfFieldId(PDF_SOURCE_ID_2)
                            .investigationPdfFieldId(INVESTIGATION_PDF_FIELD_ID_2)
                            .investigationPdfOptions(getInvestigationPdfOptions())
                            .build(),
                        MEDICAL_INVESTIGATION_FIELD_ID_3,
                        PdfConfigurationMedicalInvestigation.builder()
                            .datePdfFieldId(new PdfFieldId(PDF_DATE_ID_3))
                            .sourceTypePdfFieldId(PDF_SOURCE_ID_3)
                            .investigationPdfFieldId(INVESTIGATION_PDF_FIELD_ID_3)
                            .investigationPdfOptions(getInvestigationPdfOptions())
                            .build()
                    )
                )
                .build()
        )
        .children(List.of(children))
        .build();
  }

  private static Map<String, String> getInvestigationPdfOptions() {
    return Stream.of(
            NEUROPSYKIATRISKT,
            HABILITERING,
            ARBETSTERAPEUT,
            FYSIOTERAPEUT,
            LOGOPED,
            PSYKOLOG,
            VARDCENTRAL,
            SPECIALISTKLINIK,
            VARD_UTOMLANDS,
            HORSELHABILITERING,
            SYNHABILITERING,
            DIETIST,
            OVRIGT
        )
        .collect(Collectors.toMap(
                Code::code,
                Code::displayName
            )
        );
  }

  private static MedicalInvestigationConfig getMedicalInvestigationConfig(FieldId fieldId) {
    return MedicalInvestigationConfig.builder()
        .id(fieldId)
        .dateId(getDateId(fieldId))
        .investigationTypeId(getInvestigationTypeId(fieldId))
        .informationSourceId(getInformationSourceId(fieldId))
        .typeOptions(
            List.of(
                NEUROPSYKIATRISKT,
                HABILITERING,
                ARBETSTERAPEUT,
                FYSIOTERAPEUT,
                LOGOPED,
                PSYKOLOG,
                VARDCENTRAL,
                SPECIALISTKLINIK,
                VARD_UTOMLANDS,
                HORSELHABILITERING,
                SYNHABILITERING,
                DIETIST,
                OVRIGT
            )
        )
        .min(null)
        .max(Period.ofDays(0))
        .build();
  }

  private static FieldId getInformationSourceId(FieldId fieldId) {
    return new FieldId(fieldId.value() + "_INFORMATION_SOURCE");
  }

  private static FieldId getInvestigationTypeId(FieldId fieldId) {
    return new FieldId(fieldId.value() + "_INVESTIGATION_TYPE");
  }

  private static FieldId getDateId(FieldId fieldId) {
    return new FieldId(fieldId.value() + "_DATE");
  }
}