package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPrognos {

  public static final ElementId QUESTION_PROGNOS_ID = new ElementId(
      "51");
  private static final FieldId QUESTION_PROGNOS_FIELD_ID = new FieldId("51.1");
  
  public static ElementSpecification questionPrognos() {
    return ElementSpecification.builder()
        .id(QUESTION_PROGNOS_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_PROGNOS_FIELD_ID)
                .name(
                    "Hur förväntas patientens funktionsnedsättning och aktivitetsbegränsningar utvecklas över tid?")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PROGNOS_ID,
                    QUESTION_PROGNOS_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_PROGNOS_ID,
                    (short) 4000)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }
}
