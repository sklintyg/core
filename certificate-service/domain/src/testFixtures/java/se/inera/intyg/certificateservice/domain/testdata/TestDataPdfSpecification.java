package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_PDF_PATH_NO_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_FODELSEDATUM_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_MCID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_PATH_NO_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_QUESTION_BERAKNAT_FODELSEDATUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_AKUT_LIVSHOTANDE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_ANNAT_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_DIAGNOS_1;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_ENDAST_PALLIATIV_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_ACUTE_LIFE_THREATENING_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_CAN_CONSENT_NO_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_CAN_CONSENT_YES_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_CAN_PATIENT_CONSENT_FIELD_ID_PREFIX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_CONDITION_IS_LIFE_THREATENING_OTHER_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_CONDITION_IS_LIFE_THREATENING_TO_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_DIAGNOSE_ID_1;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_DIAGNOSIS_FIELD_ID_PREFIX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_ESTIMATE_HOW_LONG_CONDITION_WILL_BE_LIFE_THREATENING_FIELD_ID_PREFIX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_ESTIMATE_NO_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_ESTIMATE_YES_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_MCID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_ONLY_PALLIATIVE_CARE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_OTHER_THREAT_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNED_BY_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNED_BY_PA_TITLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNED_BY_SPECIALTY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNED_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_STATEMENT_BASED_ON_FIELD_ID_PREFIX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_STATEMENT_BASED_ON_INVESTIGATION_CHECKBOX_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_STATEMENT_BASED_ON_INVESTIGATION_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_STATEMENT_BASED_ON_JOURNAL_CHECKBOX_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_STATEMENT_BASED_ON_JOURNAL_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_STATEMENT_BASED_ON_OTHER_CHECKBOX_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_STATEMENT_BASED_ON_OTHER_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_STATEMENT_BASED_ON_OTHER_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_TANGIBLE_THREAT_TO_PATIENTS_LIFE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_TREATMENT_AND_CARE_SITUATION_FIELD_ID_PREFIX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_WHEN_ACTIVE_TREATMENT_WAS_STOPPED_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_WHEN_CONDITION_BECAME_LIFE_THREATENING_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_WORKPLACE_CODE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_QUESTION_UTLATANDE_BASERAT_PA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_PA_TITLE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_SIGNED_BY_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_SIGNED_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_SPECIALTY_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_WORKPLACE_CODE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_MCID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_PATH_NO_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_PA_TITLE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_PERIOD_FIELD_ID_PREFIX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNED_BY_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNED_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SPECIALTY_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SYMPTOM_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_WORKPLACE_CODE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_QUESTION_SYMPTOM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_DIAGNOS_2;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_DIAGNOS_3;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_DIAGNOS_4;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_DIAGNOS_5;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_1_1;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_1_2;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_1_3;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_1_4;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_1_5;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_2_1;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_2_2;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_2_3;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_2_4;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_2_5;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_3_1;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_3_2;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_3_3;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_3_4;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_3_5;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_4_1;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_4_2;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_4_3;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_4_4;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_4_5;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_5_1;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_5_2;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_5_3;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_5_4;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_CODE_ID_5_5;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_DIAGNOSE_ID_2;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_DIAGNOSE_ID_3;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_DIAGNOSE_ID_4;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_DIAGNOSE_ID_5;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfQuestionField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfValueType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.QuestionConfigurationDiagnose;

public class TestDataPdfSpecification {

  public static final PdfSpecification FK7210_PDF_SPECIFICATION = fk7210PdfSpecification();
  public static final PdfSpecification FK3226_PDF_SPECIFICATION = fk3226PdfSpecification();
  public static final PdfSpecification FK7472_PDF_SPECIFICATION = fk7472PdfSpecification();
  public static final PdfSpecification FK7809_PDF_SPECIFICATION = fk7809PdfSpecification();

  public static PdfSpecification fk7210PdfSpecification() {
    return PdfSpecification.builder()
        .certificateType(FK7210_TYPE)
        .signature(PdfSignature.builder()
            .signedDateFieldId(FK7210_PDF_SIGNED_DATE_FIELD_ID)
            .signedByNameFieldId(FK7210_PDF_SIGNED_BY_NAME_FIELD_ID)
            .paTitleFieldId(FK7210_PDF_PA_TITLE_FIELD_ID)
            .specialtyFieldId(FK7210_PDF_SPECIALTY_FIELD_ID)
            .hsaIdFieldId(FK7210_PDF_HSA_ID_FIELD_ID)
            .workplaceCodeFieldId(FK7210_PDF_WORKPLACE_CODE_FIELD_ID)
            .contactInformation(FK7210_PDF_CONTACT_INFORMATION)
            .build())
        .pdfTemplatePath(FK7210_PDF_PATH)
        .pdfNoAddressTemplatePath(FK7210_PDF_PATH_NO_ADDRESS)
        .patientIdFieldId(FK7210_PDF_PATIENT_ID_FIELD_ID)
        .mcid(FK7210_PDF_MCID)
        .signatureWithAddressTagIndex(FK7210_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
        .signatureWithoutAddressTagIndex(FK7210_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
        .questionFields(
            List.of(
                PdfQuestionField.builder()
                    .questionId(FK7210_QUESTION_BERAKNAT_FODELSEDATUM_ID)
                    .pdfFieldId(FK7210_PDF_FODELSEDATUM_FIELD_ID)
                    .pdfValueType(PdfValueType.DATE)
                    .build()
            )
        )
        .build();
  }

  public static PdfSpecification fk3226PdfSpecification() {
    return PdfSpecification.builder()
        .certificateType(FK3226_TYPE)
        .signature(PdfSignature.builder()
            .signedDateFieldId(FK3226_PDF_SIGNED_DATE_FIELD_ID)
            .signedByNameFieldId(FK3226_PDF_SIGNED_BY_NAME_FIELD_ID)
            .paTitleFieldId(FK3226_PDF_SIGNED_BY_PA_TITLE)
            .specialtyFieldId(FK3226_PDF_SIGNED_BY_SPECIALTY)
            .hsaIdFieldId(FK3226_PDF_HSA_ID_FIELD_ID)
            .workplaceCodeFieldId(FK3226_PDF_WORKPLACE_CODE_FIELD_ID)
            .contactInformation(FK3226_PDF_CONTACT_INFORMATION)
            .build())
        .pdfTemplatePath(FK3226_PDF_PATH)
        .pdfNoAddressTemplatePath(FK3226_PDF_PATH_NO_ADDRESS)
        .patientIdFieldId(FK3226_PDF_PATIENT_ID_FIELD_ID)
        .mcid(FK3226_PDF_MCID)
        .signatureWithAddressTagIndex(FK3226_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
        .signatureWithoutAddressTagIndex(FK3226_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
        .questionFields(
            List.of(
                PdfQuestionField.builder()
                    .questionId(FK3226_QUESTION_UTLATANDE_BASERAT_PA_ID)
                    .pdfFieldId(FK3226_PDF_STATEMENT_BASED_ON_FIELD_ID_PREFIX)
                    .pdfValueType(PdfValueType.DATE_LIST)
                    .questionConfiguration(List.of(
                        QuestionConfigurationDateList.builder()
                            .questionFieldId(
                                FK3226_UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID)
                            .checkboxFieldId(
                                FK3226_PDF_STATEMENT_BASED_ON_INVESTIGATION_CHECKBOX_FIELD_ID)
                            .dateFieldId(FK3226_PDF_STATEMENT_BASED_ON_INVESTIGATION_DATE_FIELD_ID)
                            .build(),
                        QuestionConfigurationDateList.builder()
                            .questionFieldId(
                                FK3226_UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID)
                            .checkboxFieldId(
                                FK3226_PDF_STATEMENT_BASED_ON_JOURNAL_CHECKBOX_FIELD_ID)
                            .dateFieldId(FK3226_PDF_STATEMENT_BASED_ON_JOURNAL_DATE_FIELD_ID)
                            .build(),
                        QuestionConfigurationDateList.builder()
                            .questionFieldId(
                                FK3226_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID)
                            .checkboxFieldId(FK3226_PDF_STATEMENT_BASED_ON_OTHER_CHECKBOX_FIELD_ID)
                            .dateFieldId(FK3226_PDF_STATEMENT_BASED_ON_OTHER_DATE_FIELD_ID)
                            .build()
                    ))
                    .build(),
                PdfQuestionField.builder()
                    .questionId(FK3226_QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID)
                    .pdfFieldId(FK3226_PDF_STATEMENT_BASED_ON_OTHER_FIELD_ID)
                    .pdfValueType(PdfValueType.TEXT)
                    .build(),
                PdfQuestionField.builder()
                    .questionId(FK3226_DIAGNOSIS_ID)
                    .pdfFieldId(FK3226_PDF_DIAGNOSIS_FIELD_ID_PREFIX)
                    .pdfValueType(PdfValueType.DIAGNOSE_LIST)
                    .questionConfiguration(List.of(
                        QuestionConfigurationDiagnose.builder()
                            .questionId(FK3226_DIAGNOS_1)
                            .diagnoseNameFieldId(FK3226_PDF_DIAGNOSE_ID_1)
                            .diagnoseCodeFieldIds(List.of(
                                FK_3226_PDF_CODE_ID_1_1, FK_3226_PDF_CODE_ID_1_2,
                                FK_3226_PDF_CODE_ID_1_3, FK_3226_PDF_CODE_ID_1_4,
                                FK_3226_PDF_CODE_ID_1_5
                            )).build(),
                        QuestionConfigurationDiagnose.builder()
                            .questionId(FK_3226_DIAGNOS_2)
                            .diagnoseNameFieldId(FK_3226_PDF_DIAGNOSE_ID_2)
                            .diagnoseCodeFieldIds(List.of(
                                FK_3226_PDF_CODE_ID_2_1, FK_3226_PDF_CODE_ID_2_2,
                                FK_3226_PDF_CODE_ID_2_3, FK_3226_PDF_CODE_ID_2_4,
                                FK_3226_PDF_CODE_ID_2_5
                            )).build(),
                        QuestionConfigurationDiagnose.builder()
                            .questionId(FK_3226_DIAGNOS_3)
                            .diagnoseNameFieldId(FK_3226_PDF_DIAGNOSE_ID_3)
                            .diagnoseCodeFieldIds(List.of(
                                FK_3226_PDF_CODE_ID_3_1, FK_3226_PDF_CODE_ID_3_2,
                                FK_3226_PDF_CODE_ID_3_3, FK_3226_PDF_CODE_ID_3_4,
                                FK_3226_PDF_CODE_ID_3_5
                            )).build(),
                        QuestionConfigurationDiagnose.builder()
                            .questionId(FK_3226_DIAGNOS_4)
                            .diagnoseNameFieldId(FK_3226_PDF_DIAGNOSE_ID_4)
                            .diagnoseCodeFieldIds(List.of(
                                FK_3226_PDF_CODE_ID_4_1, FK_3226_PDF_CODE_ID_4_2,
                                FK_3226_PDF_CODE_ID_4_3, FK_3226_PDF_CODE_ID_4_4,
                                FK_3226_PDF_CODE_ID_4_5
                            )).build(),
                        QuestionConfigurationDiagnose.builder()
                            .questionId(FK_3226_DIAGNOS_5)
                            .diagnoseNameFieldId(FK_3226_PDF_DIAGNOSE_ID_5)
                            .diagnoseCodeFieldIds(List.of(
                                FK_3226_PDF_CODE_ID_5_1, FK_3226_PDF_CODE_ID_5_2,
                                FK_3226_PDF_CODE_ID_5_3,
                                FK_3226_PDF_CODE_ID_5_4, FK_3226_PDF_CODE_ID_5_5
                            )).build()
                    ))
                    .build(),
                PdfQuestionField.builder()
                    .questionId(FK3226_QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID)
                    .pdfFieldId(FK3226_PDF_TREATMENT_AND_CARE_SITUATION_FIELD_ID_PREFIX)
                    .pdfValueType(PdfValueType.CODE)
                    .questionConfiguration(List.of(
                        QuestionConfigurationCode.builder()
                            .questionFieldId(FK3226_ENDAST_PALLIATIV_FIELD_ID)
                            .pdfFieldId(FK3226_PDF_ONLY_PALLIATIVE_CARE_FIELD_ID)
                            .build(),
                        QuestionConfigurationCode.builder()
                            .questionFieldId(FK3226_AKUT_LIVSHOTANDE_FIELD_ID)
                            .pdfFieldId(FK3226_PDF_ACUTE_LIFE_THREATENING_FIELD_ID)
                            .build(),
                        QuestionConfigurationCode.builder()
                            .questionFieldId(FK3226_ANNAT_FIELD_ID)
                            .pdfFieldId(FK3226_PDF_OTHER_THREAT_FIELD_ID)
                            .build()
                    ))
                    .build(),
                PdfQuestionField.builder()
                    .questionId(FK3226_QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID)
                    .pdfFieldId(FK3226_PDF_WHEN_ACTIVE_TREATMENT_WAS_STOPPED_FIELD_ID)
                    .pdfValueType(PdfValueType.DATE)
                    .build(),
                PdfQuestionField.builder()
                    .questionId(FK3226_QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID)
                    .pdfFieldId(FK3226_PDF_WHEN_CONDITION_BECAME_LIFE_THREATENING_FIELD_ID)
                    .pdfValueType(PdfValueType.DATE)
                    .build(),
                PdfQuestionField.builder()
                    .questionId(
                        FK3226_QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_ID)
                    .pdfFieldId(FK3226_PDF_TANGIBLE_THREAT_TO_PATIENTS_LIFE_FIELD_ID)
                    .pdfValueType(PdfValueType.TEXT)
                    .build(),
                PdfQuestionField.builder()
                    .questionId(
                        FK3226_QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID)
                    .pdfFieldId(
                        FK3226_PDF_ESTIMATE_HOW_LONG_CONDITION_WILL_BE_LIFE_THREATENING_FIELD_ID_PREFIX)
                    .pdfValueType(PdfValueType.BOOLEAN)
                    .questionConfiguration(List.of(
                        QuestionConfigurationBoolean.builder()
                            .questionId(
                                FK3226_QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID)
                            .checkboxTrue(FK3226_PDF_ESTIMATE_YES_FIELD_ID)
                            .checkboxFalse(FK3226_PDF_ESTIMATE_NO_FIELD_ID)
                            .build()
                    ))
                    .build(),
                PdfQuestionField.builder()
                    .questionId(FK3226_QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_ID)
                    .pdfFieldId(FK3226_PDF_CONDITION_IS_LIFE_THREATENING_TO_FIELD_ID)
                    .pdfValueType(PdfValueType.DATE)
                    .build(),
                PdfQuestionField.builder()
                    .questionId(FK3226_QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID)
                    .pdfFieldId(FK3226_PDF_CONDITION_IS_LIFE_THREATENING_OTHER_FIELD_ID)
                    .pdfValueType(PdfValueType.TEXT)
                    .build(),
                PdfQuestionField.builder()
                    .questionId(FK3226_FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID)
                    .pdfFieldId(FK3226_PDF_CAN_PATIENT_CONSENT_FIELD_ID_PREFIX)
                    .pdfValueType(PdfValueType.BOOLEAN)
                    .questionConfiguration(List.of(
                        QuestionConfigurationBoolean.builder()
                            .questionId(
                                FK3226_FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID)
                            .checkboxTrue(FK3226_PDF_CAN_CONSENT_YES_FIELD_ID)
                            .checkboxFalse(FK3226_PDF_CAN_CONSENT_NO_FIELD_ID)
                            .build()
                    ))
                    .build())
        ).build();
  }

  public static PdfSpecification fk7472PdfSpecification() {
    return PdfSpecification.builder()
        .certificateType(FK7472_TYPE)
        .signature(PdfSignature.builder()
            .signedDateFieldId(FK7472_PDF_SIGNED_DATE_FIELD_ID)
            .signedByNameFieldId(FK7472_PDF_SIGNED_BY_NAME_FIELD_ID)
            .paTitleFieldId(FK7472_PDF_PA_TITLE_FIELD_ID)
            .specialtyFieldId(FK7472_PDF_SPECIALTY_FIELD_ID)
            .hsaIdFieldId(FK7472_PDF_HSA_ID_FIELD_ID)
            .workplaceCodeFieldId(FK7472_PDF_WORKPLACE_CODE_FIELD_ID)
            .contactInformation(FK7472_PDF_CONTACT_INFORMATION)
            .build())
        .pdfTemplatePath(FK7472_PDF_PATH)
        .pdfNoAddressTemplatePath(FK7472_PDF_PATH_NO_ADDRESS)
        .patientIdFieldId(FK7472_PDF_PATIENT_ID_FIELD_ID)
        .mcid(FK7472_PDF_MCID)
        .signatureWithAddressTagIndex(FK7472_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
        .signatureWithoutAddressTagIndex(FK7472_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
        .questionFields(
            List.of(
                PdfQuestionField.builder()
                    .questionId(FK7472_QUESTION_SYMPTOM_ID)
                    .pdfFieldId(FK7472_PDF_SYMPTOM_FIELD_ID)
                    .pdfValueType(PdfValueType.TEXT)
                    .build(),
                PdfQuestionField.builder()
                    .questionId(FK7472_QUESTION_PERIOD_ID)
                    .pdfFieldId(FK7472_PDF_PERIOD_FIELD_ID_PREFIX)
                    .pdfValueType(PdfValueType.DATE_RANGE_LIST)
                    .build()
            )
        ).build();
  }

  public static PdfSpecification fk7809PdfSpecification() {
    return PdfSpecification.builder().build();
  }
}
