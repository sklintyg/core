package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionFormedlaDiagnos {

  public static final ElementId QUESTION_FORMEDLA_DIAGNOS_ID = new ElementId("3");
  public static final FieldId QUESTION_FORMEDLA_DIAGNOS_FIELD_ID = new FieldId("3.1");

  private QuestionFormedlaDiagnos() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionFormedlaDiagnos() {
    return ElementSpecification.builder()
        .id(QUESTION_FORMEDLA_DIAGNOS_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_FORMEDLA_DIAGNOS_FIELD_ID)
                .name(
                    "Önskar patienten förmedla information om diagnos/diagnoser till sin arbetsgivare?")
                .description(
                    "Information om diagnos kan vara viktig för patientens arbetsgivare. Det kan underlätta anpassning av patientens arbetssituation. Det kan också göra att patienten snabbare kommer tillbaka till arbetet.")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_FORMEDLA_DIAGNOS_ID,
                    QUESTION_FORMEDLA_DIAGNOS_FIELD_ID
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
        .build();
  }
}
