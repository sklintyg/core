package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationDateTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("code");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");
  private ElementValidationDate elementValidationDate;

  @Nested
  class IllegalStates {

    @BeforeEach
    void setUp() {
      elementValidationDate = ElementValidationDate.builder().build();
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfDataIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDate.validate(null, categoryId, Collections.emptyList())
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDate.validate(elementData, categoryId, Collections.emptyList()));
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueUnitContactInformation.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDate.validate(elementData, categoryId, Collections.emptyList()));
    }
  }

  @Nested
  class Mandatory {

    @BeforeEach
    void setUp() {
      elementValidationDate = ElementValidationDate.builder()
          .mandatory(true)
          .min(Period.ofDays(0))
          .max(Period.ofDays(30))
          .build();
    }

    @Test
    void shallReturnValidationErrorIfDateIsNull() {
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
              ElementValueDate.builder()
                  .dateId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallReturnValidationErrorIfDateIsBeforeMin() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(
                  new ErrorMessage("Ange ett datum som är tidigast %s.".formatted(LocalDate.now())))
              .build()
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDate.builder()
                  .dateId(FIELD_ID)
                  .date(LocalDate.now().minusDays(1))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallReturnValidationErrorIfDateIsAfterMax() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(
                  new ErrorMessage(
                      "Ange ett datum som är senast %s.".formatted(LocalDate.now().plusDays(30))))
              .build()
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDate.builder()
                  .dateId(FIELD_ID)
                  .date(LocalDate.now().plusDays(31))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(expectedValidationError, actualResult);
    }
  }

  @Nested
  class NotMandatory {

    @BeforeEach
    void setUp() {
      elementValidationDate = ElementValidationDate.builder()
          .min(Period.ofDays(0))
          .max(Period.ofDays(30))
          .build();
    }

    @Test
    void shallReturnValidationErrorEmptyIfDateIsNull() {
      final var expectedValidationError = Collections.emptyList();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDate.builder()
                  .dateId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallReturnValidationErrorIfDateIsBeforeMin() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(
                  new ErrorMessage("Ange ett datum som är tidigast %s.".formatted(LocalDate.now())))
              .build()
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDate.builder()
                  .dateId(FIELD_ID)
                  .date(LocalDate.now().minusDays(1))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallReturnValidationErrorIfDateIsAfterMax() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(
                  new ErrorMessage(
                      "Ange ett datum som är senast %s.".formatted(LocalDate.now().plusDays(30))))
              .build()
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDate.builder()
                  .dateId(FIELD_ID)
                  .date(LocalDate.now().plusDays(31))
                  .build()
          )
          .build();

      final var actualResult = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(expectedValidationError, actualResult);
    }
  }

  @Nested
  class NoLimits {

    @BeforeEach
    void setUp() {
      elementValidationDate = ElementValidationDate.builder().build();
    }

    @Test
    void shallReturnValidationErrorEmptyIfDateIsNull() {
      final var expectedValidationError = Collections.emptyList();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDate.builder()
                  .dateId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallReturnValidationErrorEmptyIfDateIsDefined() {
      final var expectedValidationError = Collections.emptyList();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDate.builder()
                  .dateId(FIELD_ID)
                  .date(LocalDate.now())
                  .build()
          )
          .build();

      final var actualResult = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }
  }
}