package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;

public class QuestionDiagnos {

  public static final FieldId DIAGNOSIS_FIELD_ID = new FieldId("58.1");
  public static final ElementId DIAGNOSIS_ID = new ElementId("58");
  public static final FieldId DIAGNOS_1 = new FieldId("huvuddiagnos");
  public static final FieldId DIAGNOS_2 = new FieldId("diagnos2");
  public static final FieldId DIAGNOS_3 = new FieldId("diagnos3");

  private static final short DIAGNOSIS_DESCRIPTION_LIMIT = (short) 81;

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
                    "Barnets diagnos")
                .terminology(
                    List.of(
                        CodeSystemIcd10Se.terminology()
                    )
                )
                .list(
                    List.of(
                        new ElementDiagnosisListItem(DIAGNOS_1),
                        new ElementDiagnosisListItem(DIAGNOS_2),
                        new ElementDiagnosisListItem(DIAGNOS_3)
                    )
                )
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(DIAGNOSIS_ID, DIAGNOSIS_DESCRIPTION_LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationDiagnosis.builder()
                    .order(
                        List.of(DIAGNOS_1, DIAGNOS_2, DIAGNOS_3)
                    )
                    .diagnosisCodeRepository(diagnosisCodeRepository)
                    .build()
            )
        )
        .build();
  }
}