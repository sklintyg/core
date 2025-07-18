package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRange;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionDateRange {


  public static final ElementId QUESTION_DATE_RANGE_ID = new ElementId("6");
  private static final FieldId QUESTION_DATE_RANGE_FIELD_ID = new FieldId("6");

  private QuestionDateRange() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDateRange() {
    return ElementSpecification.builder()
        .id(QUESTION_DATE_RANGE_ID)
        .configuration(
            ElementConfigurationDateRange.builder()
                .name("Test för \"DATE_RANGE\"")
                .labelFrom("Fr.o.m")
                .labelTo("T.o.m")
                .id(QUESTION_DATE_RANGE_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_DATE_RANGE_ID,
                    QUESTION_DATE_RANGE_FIELD_ID)
            )
        )
        .validations(
            List.of(
                ElementValidationDateRange.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }
}
