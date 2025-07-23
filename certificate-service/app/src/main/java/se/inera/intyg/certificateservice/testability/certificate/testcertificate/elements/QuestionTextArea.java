package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

public class QuestionTextArea {

  public static final ElementId QUESTION_TEXT_AREA_ID = new ElementId("12");
  public static final FieldId QUESTION_TEXT_AREA_FIELD_ID = new FieldId("12.1");

  private QuestionTextArea() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionTextArea() {
    return ElementSpecification.builder()
        .id(QUESTION_TEXT_AREA_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_TEXT_AREA_FIELD_ID)
                .name("TEXT_AREA")
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