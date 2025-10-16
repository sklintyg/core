package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenPdfConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;

public class QuestionDiagnos {

  public static final ElementId QUESTION_DIAGNOS_ID = new ElementId("4");
  public static final FieldId QUESTION_DIAGNOS_FIELD_ID = new FieldId("4.1");
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
                .id(QUESTION_DIAGNOS_FIELD_ID)
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
            CertificateElementRuleFactory.mandatoryExist(QUESTION_DIAGNOS_ID, DIAGNOS_1),
            CertificateElementRuleFactory.show(QUESTION_FORMEDLA_DIAGNOS_ID,
                QUESTION_FORMEDLA_DIAGNOS_FIELD_ID),
            CertificateElementRuleFactory.showEmpty(QUESTION_FORMEDLA_DIAGNOS_ID,
                QUESTION_FORMEDLA_DIAGNOS_FIELD_ID),
            CertificateElementRuleFactory.disableEmptyElement(QUESTION_FORMEDLA_DIAGNOS_ID,
                QUESTION_FORMEDLA_DIAGNOS_FIELD_ID)
        ))
        .validations(List.of(
            ElementValidationDiagnosis.builder()
                .mandatoryField(DIAGNOS_1)
                .order(List.of(DIAGNOS_1, DIAGNOS_2, DIAGNOS_3))
                .diagnosisCodeRepository(diagnosisCodeRepository)
                .build()
        ))
        .shouldValidate(
            ElementDataPredicateFactory.radioBooleans(List.of(QUESTION_FORMEDLA_DIAGNOS_ID), true))
        .mapping(new ElementMapping(CustomMapperId.UNIFIED_DIAGNOSIS_LIST))
        .pdfConfiguration(
            CitizenPdfConfiguration.builder()
                .hiddenBy(QUESTION_DIAGNOS_ID)
                .shouldHide(
                    ElementDataPredicateFactory.radioBooleans(List.of(QUESTION_FORMEDLA_DIAGNOS_ID),
                        false)
                )
                .replacementValue(
                    ElementSimplifiedValueText.builder()
                        .text("På patientens begäran uppges inte diagnos")
                        .build()
                )
                .build()
        )
        .build();
  }
}
