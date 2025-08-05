package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class ElementValidationIntegerTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("code");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");
  private ElementValidationInteger elementValidationInteger;

  @Nested
  class IllegalStates {

    @BeforeEach
    void setUp() {
      elementValidationInteger = ElementValidationInteger.builder().build();
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfDataIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      assertThrows(IllegalArgumentException.class,
          () -> elementValidationInteger.validate(null, categoryId, Collections.emptyList())
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationInteger.validate(elementData, categoryId, Collections.emptyList())
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueText.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationInteger.validate(elementData, categoryId, Collections.emptyList())
      );
    }
  }

  @Nested
  class Mandatory {

    @BeforeEach
    void setUp() {
      elementValidationInteger = ElementValidationInteger.builder()
          .mandatory(true)
          .min(1)
          .max(10)
          .build();
    }

    @Test
    void shallReturnValidationErrorIfIntegerIsNull() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange ett svar."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallReturnValidationErrorIfIntegerIsBelowMin() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange ett värde mellan 1-10."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .value(0)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallReturnValidationErrorIfIntegerIsAboveMax() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange ett värde mellan 1-10."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .value(11)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallNotReturnValidationErrorIfIntegerIsWithinLimits() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .value(5)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }
  }

  @Nested
  class NotMandatory {

    @BeforeEach
    void setUp() {
      elementValidationInteger = ElementValidationInteger.builder()
          .mandatory(false)
          .min(1)
          .max(10)
          .build();
    }

    @Test
    void shallNotReturnValidationErrorIfIntegerIsNull() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void shallReturnValidationErrorIfIntegerIsBelowMin() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange ett värde mellan 1-10."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .value(0)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallReturnValidationErrorIfIntegerIsAboveMax() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange ett värde mellan 1-10."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .value(11)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallNotReturnValidationErrorIfIntegerIsWithinLimits() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .value(5)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }
  }

  @Nested
  class NoLimits {

    @BeforeEach
    void setUp() {
      elementValidationInteger = ElementValidationInteger.builder()
          .build();
    }

    @Test
    void shallNotReturnValidationErrorIfIntegerExists() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .value(5)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void shallNotReturnValidationErrorIfIntegerIsNull() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueInteger.builder()
                  .integerId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationInteger.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }
  }
}

