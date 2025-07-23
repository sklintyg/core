package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

public class QuestionRadioBoolean {


  public static final ElementId QUESTION_RADIO_BOOLEAN_ID = new ElementId("10");
  public static final FieldId QUESTION_RADIO_BOOLEAN_FIELD_ID = new FieldId("10.1");

  private QuestionRadioBoolean() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionRadioBoolean() {
    return ElementSpecification.builder()
        .id(QUESTION_RADIO_BOOLEAN_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_RADIO_BOOLEAN_FIELD_ID)
                .name("RADIO_BOOLEAN")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .build();
  }
}