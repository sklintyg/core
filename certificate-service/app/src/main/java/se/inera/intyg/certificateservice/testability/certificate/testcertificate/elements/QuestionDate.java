package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;

public class QuestionDate {


  public static final ElementId QUESTION_DATE_ID = new ElementId("15");
  private static final FieldId QUESTION_DATE_FIELD_ID = new FieldId("15.1");

  private QuestionDate() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDate() {
    return ElementSpecification.builder()
        .id(QUESTION_DATE_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .name("DATE")
                .id(QUESTION_DATE_FIELD_ID)
                .build()
        )
        .validations(
            List.of(
                ElementValidationDate.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .build();
  }
}