package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.time.Period;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;

public class QuestionCheckboxMultipleDate {

  public static final ElementId QUESTION_CHECKBOX_MULTIPLE_DATE_ID = new ElementId("4");
  public static final FieldId QUESTION_CHECKBOX_MULTIPLE_DATE_FIELD_ID = new FieldId("4.1");


  private QuestionCheckboxMultipleDate() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionCheckboxMultipleDate(
      ElementSpecification... children) {
    final var checkboxDates = List.of(
        CheckboxDate.builder()
            .id(new FieldId("1"))
            .label("Test 1")
            .code(new Code("1", "Test 1", "Test 1"))
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(new FieldId("2"))
            .label("Test 2")
            .code(new Code("2", "Test 2", "Test 2"))
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(new FieldId("3"))
            .label("Test 3")
            .code(new Code("3", "Test 3", "Test 3"))
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(new FieldId("4"))
            .label("Test 4")
            .code(new Code("4", "Test 4", "Test 4"))
            .min(null)
            .max(Period.ofDays(0))
            .build()
    );

    return ElementSpecification.builder()
        .id(QUESTION_CHECKBOX_MULTIPLE_DATE_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleDate.builder()
                .id(QUESTION_CHECKBOX_MULTIPLE_DATE_FIELD_ID)
                .name("CHECKBOX_MULTIPLE_DATE")
                .dates(checkboxDates)
                .build()
        )
        .validations(
            List.of(
                ElementValidationDateList.builder()
                    .mandatory(false)
                    .max(Period.ofDays(0))
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }
}