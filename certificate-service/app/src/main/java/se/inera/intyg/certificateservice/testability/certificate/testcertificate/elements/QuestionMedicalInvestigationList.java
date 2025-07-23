package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.time.Period;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationMedicalInvestigationList;

public class QuestionMedicalInvestigationList {

  public static final ElementId QUESTION_MEDICAL_INVESTIGATION_LIST_ID = new ElementId("9");
  private static final FieldId QUESTION_MEDICAL_INVESTIGATION_LIST_FIELD_ID = new FieldId("9.1");

  public static final FieldId MEDICAL_INVESTIGATION_FIELD_ID_1 = new FieldId(
      "medicalInvestigation1");
  public static final FieldId MEDICAL_INVESTIGATION_FIELD_ID_2 = new FieldId(
      "medicalInvestigation2");
  public static final FieldId MEDICAL_INVESTIGATION_FIELD_ID_3 = new FieldId(
      "medicalInvestigation3");


  public static final int LIMIT = 53;

  private QuestionMedicalInvestigationList() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedicalInvestigationList(
      ElementSpecification... children) {
    final var medicalInvestigations = List.of(
        getMedicalInvestigationConfig(MEDICAL_INVESTIGATION_FIELD_ID_1),
        getMedicalInvestigationConfig(MEDICAL_INVESTIGATION_FIELD_ID_2),
        getMedicalInvestigationConfig(MEDICAL_INVESTIGATION_FIELD_ID_3)
    );

    return ElementSpecification.builder()
        .id(QUESTION_MEDICAL_INVESTIGATION_LIST_ID)
        .configuration(
            ElementConfigurationMedicalInvestigationList.builder()
                .id(QUESTION_MEDICAL_INVESTIGATION_LIST_FIELD_ID)
                .name("MEDICAL_INVESTIGATION_LIST")
                .informationSourceDescription("")
                .dateText("Datum")
                .typeText("Typ")
                .informationSourceText("Från")
                .list(medicalInvestigations)
                .build()
        )
        .validations(
            List.of(
                ElementValidationMedicalInvestigationList.builder()
                    .mandatory(false)
                    .max(Period.ofDays(0))
                    .limit(LIMIT)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }

  private static MedicalInvestigationConfig getMedicalInvestigationConfig(FieldId fieldId) {
    return MedicalInvestigationConfig.builder()
        .id(fieldId)
        .dateId(getDateId(fieldId))
        .investigationTypeId(getInvestigationTypeId(fieldId))
        .informationSourceId(getInformationSourceId(fieldId))
        .typeOptions(
            List.of(
                new Code("KOD_1", "TEST", "TESTKOD 1"),
                new Code("KOD_2", "TEST", "TESTKOD 2"),
                new Code("KOD_3", "TEST", "TESTKOD 3"),
                new Code("KOD_4", "TEST", "TESTKOD 4"),
                new Code("KOD_5", "TEST", "TESTKOD 5"),
                new Code("KOD_6", "TEST", "TESTKOD 6"),
                new Code("KOD_7", "TEST", "TESTKOD 7")
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