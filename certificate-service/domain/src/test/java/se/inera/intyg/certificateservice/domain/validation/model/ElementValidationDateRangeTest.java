package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationDateRangeTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private static final FieldId FIELD_ID_TO = new FieldId("fieldId.tom");
  private static final FieldId FIELD_ID_FROM = new FieldId("fieldId.from");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");
  private ElementValidationDateRange elementValidationDateRange;

  @Nested
  class IllegalStates {

    @BeforeEach
    void setUp() {
      elementValidationDateRange = ElementValidationDateRange.builder().build();
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfDataIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDateRange.validate(null, categoryId)
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDateRange.validate(elementData, categoryId));
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueUnitContactInformation.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDateRange.validate(elementData, categoryId));
    }
  }

  @Nested
  class Mandatory {

    @BeforeEach
    void setUp() {
      elementValidationDateRange = ElementValidationDateRange.builder()
          .mandatory(true)
          .build();
    }

    @Test
    void shouldReturnValidationErrorIfRangeIsEmpty() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange ett datum."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRange.builder()
                  .dateRangeId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRange.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnValidationErrorIfFromIsDefinedButToIsEmpty() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange ett datum."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRange.builder()
                  .dateRangeId(FIELD_ID)
                  .from(
                      ElementValueDate.builder()
                          .date(LocalDate.now())
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRange.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shouldReturnValidationErrorIfToIsDefinedButFromIsEmpty() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange ett datum."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRange.builder()
                  .dateRangeId(FIELD_ID)
                  .to(
                      ElementValueDate.builder()
                          .date(LocalDate.now())
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualResult = elementValidationDateRange.validate(
          elementData,
          Optional.of(CATEGORY_ID)
      );

      assertEquals(expectedValidationError, actualResult);
    }
  }
}