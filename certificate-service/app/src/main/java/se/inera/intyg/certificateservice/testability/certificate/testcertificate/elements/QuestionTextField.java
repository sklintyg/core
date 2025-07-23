package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

public class QuestionTextField {

  public static final ElementId QUESTION_TEXT_FIELD_ID = new ElementId("13");
  public static final FieldId QUESTION_TEXT_FIELD_FIELD_ID = new FieldId("13.1");

  private QuestionTextField() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionTextField() {
    return ElementSpecification.builder()
        .id(QUESTION_TEXT_FIELD_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_TEXT_FIELD_FIELD_ID)
                .name("TEXT_FIELD")
                .build()
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .build();
  }
}