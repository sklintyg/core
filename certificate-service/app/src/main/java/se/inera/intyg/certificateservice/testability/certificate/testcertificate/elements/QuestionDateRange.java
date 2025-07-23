package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRange;

public class QuestionDateRange {


  public static final ElementId QUESTION_DATE_RANGE_ID = new ElementId("6");
  private static final FieldId QUESTION_DATE_RANGE_FIELD_ID = new FieldId("6.1");

  private QuestionDateRange() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDateRange() {
    return ElementSpecification.builder()
        .id(QUESTION_DATE_RANGE_ID)
        .configuration(
            ElementConfigurationDateRange.builder()
                .name("DATE_RANGE")
                .labelFrom("Fr.o.m")
                .labelTo("T.o.m")
                .id(QUESTION_DATE_RANGE_FIELD_ID)
                .build()
        )
        .validations(
            List.of(
                ElementValidationDateRange.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .build();
  }
}