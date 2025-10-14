package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenPdfConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionFormedlaInfoOmDiagnosTillAG {

  public static final ElementId QUESTION_FORMEDLA_DIAGNOS_ID = new ElementId("100");
  public static final FieldId FORMEDLA_DIAGNOSIS_FIELD_ID = new FieldId("100.1");

  private QuestionFormedlaInfoOmDiagnosTillAG() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionFormedlaInfoOmDiagnosTillAG() {
    return ElementSpecification.builder()
        .id(QUESTION_FORMEDLA_DIAGNOS_ID)
        .configuration(
            ElementConfigurationRadioBoolean
                .builder()
                .id(FORMEDLA_DIAGNOSIS_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Önskar patienten förmedla information om diagnos/diagnoser till sin arbetsgivare?")
                .description(
                    "Information om diagnos kan vara viktig för patientens arbetsgivare. Det kan underlätta anpassning av patientens arbetssituation. Det kan också göra att patienten snabbare kommer tillbaka till arbetet.")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_FORMEDLA_DIAGNOS_ID,
                    List.of(
                        FORMEDLA_DIAGNOSIS_FIELD_ID
                    )
                ),
                CertificateElementRuleFactory.autofill(
                    QUESTION_SMITTBARARPENNING_ID,
                    QUESTION_SMITTBARARPENNING_FIELD_ID,
                    FORMEDLA_DIAGNOSIS_FIELD_ID
                ),
                CertificateElementRuleFactory.disableElement(
                    QUESTION_SMITTBARARPENNING_ID,
                    QUESTION_SMITTBARARPENNING_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
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
