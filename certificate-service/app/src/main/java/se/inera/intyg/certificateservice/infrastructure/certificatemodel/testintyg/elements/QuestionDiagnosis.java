package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;

public class QuestionDiagnosis {

  public static final ElementId DIAGNOSIS_ID = new ElementId("7");
  private static final FieldId DIAGNOSIS_FIELD_ID = new FieldId("7");
  public static final FieldId DIAGNOS_1 = new FieldId("huvuddiagnos");
  public static final FieldId DIAGNOS_2 = new FieldId("diagnos2");
  public static final FieldId DIAGNOS_3 = new FieldId("diagnos3");
  public static final FieldId DIAGNOS_4 = new FieldId("diagnos4");
  public static final FieldId DIAGNOS_5 = new FieldId("diagnos5");
  private static final short DIAGNOSIS_CODE_LIMIT = (short) 81;

  private QuestionDiagnosis() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDiagnosis() {
    return ElementSpecification.builder()
        .id(DIAGNOSIS_ID)
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .id(DIAGNOSIS_FIELD_ID)
                .name(
                    "Test av \"DIAGNOSIS\"")
                .message(
                    ElementMessage.builder()
                        .content(
                            "Ange alla diagnoser som sammantaget medför ett påtagligt hot mot patientens liv.")
                        .level(MessageLevel.OBSERVE)
                        .includedForStatuses(List.of(Status.DRAFT))
                        .build()
                )
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
                    .build()
            )
        )
        .build();
  }
}
