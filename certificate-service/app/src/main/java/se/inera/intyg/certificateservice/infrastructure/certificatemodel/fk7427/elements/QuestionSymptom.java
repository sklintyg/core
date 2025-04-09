package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSymptom {

  public static final ElementId QUESTION_SYMPTOM_ID = new ElementId("58.2");
  private static final FieldId QUESTION_SYMPTOM_FIELD_ID = new FieldId("58.2");
  private static final short LIMIT = 4000;

  private QuestionSymptom() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSymptom() {
    return ElementSpecification.builder()
        .id(QUESTION_SYMPTOM_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Fyll i vilka symptom barnet har om diagnos inte är fastställd")
                .id(QUESTION_SYMPTOM_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_SYMPTOM_ID, LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }
}
