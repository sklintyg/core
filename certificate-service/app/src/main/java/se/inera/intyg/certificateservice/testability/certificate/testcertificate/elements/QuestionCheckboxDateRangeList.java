package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.time.Period;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRangeList;

public class QuestionCheckboxDateRangeList {


  public static final ElementId QUESTION_CHECKBOX_DATE_RANGE_LIST_ID = new ElementId("2");
  private static final String QUESTION_CHECKBOX_DATE_RANGE_LIST_FIELD_ID = "2.1";

  private QuestionCheckboxDateRangeList() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionCheckboxDateRangeList() {
    final var dateRanges = List.of(
        new ElementConfigurationCode(
            new FieldId("1"),
            "12,5 procent",
            new Code("EN_ÅTTONDEL", "TEST", "EN ÅTTONDEL")
        ),
        new ElementConfigurationCode(
            new FieldId("2"),
            "25 procent",
            new Code("EN_FJÄRDEDAL", "TEST", "EN FJÄRDEDEL")
        ),
        new ElementConfigurationCode(
            new FieldId("3"),
            "50 procent",
            new Code("EN_HALVA", "TEST", "EN HALVA")
        ),
        new ElementConfigurationCode(
            new FieldId("4"),
            "75 procent",
            new Code("TRE_FJÄRDEDALS", "TEST", "TRE FJÄRDEDEL")
        ),
        new ElementConfigurationCode(
            new FieldId("5"),
            "100 procent",
            new Code("HELA_TIDEN", "TEST", "HELA TIDEN")
        )
    );

    return ElementSpecification.builder()
        .id(QUESTION_CHECKBOX_DATE_RANGE_LIST_ID)
        .includeWhenRenewing(false)
        .configuration(
            ElementConfigurationCheckboxDateRangeList.builder()
                .name("CHECKBOX_DATE_RANGE_LIST")
                .id(new FieldId(QUESTION_CHECKBOX_DATE_RANGE_LIST_FIELD_ID))
                .dateRanges(dateRanges)
                .min(Period.ofDays(-90))
                .build()
        )
        .validations(
            List.of(
                ElementValidationDateRangeList.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .build();
  }


}