package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionTextField {

  public static final ElementId QUESTION_TEXT_FIELD_ID = new ElementId("13");
  public static final FieldId QUESTION_TEXT_FIELD_FIELD_ID = new FieldId("13");

  private QuestionTextField() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionTextField() {
    return ElementSpecification.builder()
        .id(QUESTION_TEXT_FIELD_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_TEXT_FIELD_FIELD_ID)
                .name("Test av \"TEXT_FIELD\"")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_TEXT_FIELD_ID,
                    QUESTION_TEXT_FIELD_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }
}
