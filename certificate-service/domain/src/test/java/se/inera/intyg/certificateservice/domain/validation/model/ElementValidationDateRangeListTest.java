package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class ElementValidationDateRangeListTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private static final FieldId FIELD_ID_RANGE = new FieldId("date_range_1");
  private static final FieldId FIELD_ID_RANGE_2 = new FieldId("date_range_2");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");
  private ElementValidationDateRangeList elementValidationDateRangeList;

  @Nested
  class IllegalStates {

    @BeforeEach
    void setUp() {
      elementValidationDateRangeList = ElementValidationDateRangeList.builder().build();
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfDataIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDateRangeList.validate(null, categoryId)
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDateRangeList.validate(elementData, categoryId));
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueUnitContactInformation.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDateRangeList.validate(elementData, categoryId));
    }
  }

  @Nested
  class Mandatory {

    @BeforeEach
    void setUp() {
      elementValidationDateRangeList = ElementValidationDateRangeList.builder()
          .mandatory(true)
          .build();
    }

    @Test
    void shouldReturnValidationErrorIfEmptyListAsRanges() {
      final var expectedValidationError = getExpectedValidationErrorForMandatory();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(Collections.emptyList())
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnValidationErrorIfNullAsRanges() {
      final var expectedValidationError = getExpectedValidationErrorForMandatory();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnValidationErrorIfOnlyToDate() {
      final var expectedValidationError = getExpectedValidationError(
          "Ange ett datum.",
          new FieldId(FIELD_ID_RANGE.value() + ".from")
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(List.of(
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE)
                          .to(LocalDate.now())
                          .build()
                  ))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnValidationErrorIfOnlyFromDate() {
      final var expectedValidationError = getExpectedValidationError(
          "Ange ett datum.",
          new FieldId(FIELD_ID_RANGE.value() + ".to")
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(List.of(
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE)
                          .from(LocalDate.now())
                          .build()
                  ))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnValidationErrorIfToIsBeforeFrom() {
      final var expectedValidationError = getExpectedValidationError(
          "Ange ett slutdatum som infaller efter startdatumet.",
          new FieldId(FIELD_ID_RANGE.value() + ".range")
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(List.of(
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE)
                          .from(LocalDate.now())
                          .to(LocalDate.now().minusDays(5))
                          .build()
                  ))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnSeveralValidationErrors() {
      final var expectedValidationErrorIncorrect = getExpectedValidationError(
          "Ange ett slutdatum som infaller efter startdatumet.",
          new FieldId(FIELD_ID_RANGE.value() + ".range")
      );
      final var expectedValidationErrorIncomplete = getExpectedValidationError(
          "Ange ett datum.",
          new FieldId(FIELD_ID_RANGE_2.value() + ".to")
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(List.of(
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE)
                          .from(LocalDate.now())
                          .to(LocalDate.now().minusDays(5))
                          .build(),
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE_2)
                          .from(LocalDate.now())
                          .build()
                  ))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertAll(
          () -> assertEquals(expectedValidationErrorIncomplete.get(0), actualResult.get(0)),
          () -> assertEquals(expectedValidationErrorIncorrect.get(0), actualResult.get(1))
      );
    }
  }

  @Nested
  class NotMandatory {

    @BeforeEach
    void setUp() {
      elementValidationDateRangeList = ElementValidationDateRangeList.builder()
          .build();
    }

    @Test
    void shouldNotReturnValidationErrorIfEmptyListAsRanges() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(Collections.emptyList())
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void shouldNotReturnValidationErrorIfNullAsRanges() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void shouldReturnValidationErrorIfOnlyToDate() {
      final var expectedValidationError = getExpectedValidationError(
          "Ange ett datum.",
          new FieldId(FIELD_ID_RANGE.value() + ".from")
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(List.of(
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE)
                          .to(LocalDate.now())
                          .build()
                  ))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnValidationErrorIfOnlyFromDate() {
      final var expectedValidationError = getExpectedValidationError(
          "Ange ett datum.",
          new FieldId(FIELD_ID_RANGE.value() + ".to")
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(List.of(
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE)
                          .from(LocalDate.now())
                          .build()
                  ))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnValidationErrorIfToIsBeforeFrom() {
      final var expectedValidationError = getExpectedValidationError(
          "Ange ett slutdatum som infaller efter startdatumet.",
          new FieldId(FIELD_ID_RANGE.value() + ".range")
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(List.of(
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE)
                          .from(LocalDate.now())
                          .to(LocalDate.now().minusDays(5))
                          .build()
                  ))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnSeveralValidationErrors() {
      final var expectedValidationErrorIncorrect = getExpectedValidationError(
          "Ange ett slutdatum som infaller efter startdatumet.",
          new FieldId(FIELD_ID_RANGE.value() + ".range")
      );
      final var expectedValidationErrorIncomplete = getExpectedValidationError(
          "Ange ett datum.",
          new FieldId(FIELD_ID_RANGE_2.value() + ".to")
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(List.of(
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE)
                          .from(LocalDate.now())
                          .to(LocalDate.now().minusDays(5))
                          .build(),
                      DateRange.builder()
                          .dateRangeId(FIELD_ID_RANGE_2)
                          .from(LocalDate.now())
                          .build()
                  ))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRangeList.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertAll(
          () -> assertEquals(expectedValidationErrorIncomplete.get(0), actualResult.get(0)),
          () -> assertEquals(expectedValidationErrorIncorrect.get(0), actualResult.get(1))
      );
    }
  }

  private static List<ValidationError> getExpectedValidationError(String message, FieldId fieldId) {
    return List.of(
        ValidationError.builder()
            .elementId(ELEMENT_ID)
            .fieldId(fieldId)
            .categoryId(CATEGORY_ID)
            .message(new ErrorMessage(message))
            .build()
    );
  }

  private static List<ValidationError> getExpectedValidationErrorForMandatory() {
    return getExpectedValidationError("Välj minst ett alternativ.", FIELD_ID);
  }
}