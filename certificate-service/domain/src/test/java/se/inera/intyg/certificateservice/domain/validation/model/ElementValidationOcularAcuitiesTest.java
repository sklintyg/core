package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificate.model.WithCorrection;
import se.inera.intyg.certificateservice.domain.certificate.model.WithoutCorrection;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationOcularAcuitiesTest {

  private static final Optional<ElementId> CATEGORY_ID = Optional.of(new ElementId("id"));
  private static final String LEFT_EYE_WITHOUT_CORRECTION_ID = "LEFT_EYE_WITHOUT_CORRECTION_ID";
  private static final String RIGHT_EYE_WITHOUT_CORRECTION_ID = "RIGHT_EYE_WITHOUT_CORRECTION_ID";
  private static final String BINOCULAR_WITHOUT_CORRECTION_ID = "BINOCULAR_WITHOUT_CORRECTION_ID";
  private static final String LEFT_EYE_WITH_CORRECTION_ID = "LEFT_EYE_WITH_CORRECTION_ID";
  private static final String RIGHT_EYE_WITH_CORRECTION_ID = "RIGHT_EYE_WITH_CORRECTION_ID";
  private static final String BINOCULAR_WITH_CORRECTION_ID = "BINOCULAR_WITH_CORRECTION_ID";
  private static final double VALUE = 1.0;
  private static final ElementId ELEMENT_ID = new ElementId("1.1");
  ElementValidationOcularAcuities elementValidationOcularAcuities;

  @Nested
  class ValidateElementData {

    @BeforeEach
    void setUp() {
      elementValidationOcularAcuities = new ElementValidationOcularAcuities(
          true, 0.0, 2.0
      );
    }

    @Test
    void shallThrowIfElementDataIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> elementValidationOcularAcuities.validate(null, CATEGORY_ID));

      assertEquals("Element data is null", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfElementDataValueIsNull() {
      final var elementData = ElementData.builder().build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> elementValidationOcularAcuities.validate(elementData, CATEGORY_ID));

      assertEquals("Element data value is null", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfElementDataValueIsWrongType() {
      final var elementValueText = ElementValueText.builder().build();
      final var elementData = ElementData.builder()
          .value(elementValueText)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> elementValidationOcularAcuities.validate(elementData, CATEGORY_ID));

      assertEquals("Element data value %s is of wrong type".formatted(elementValueText.getClass()),
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class MandatoryTests {

    @BeforeEach
    void setUp() {
      elementValidationOcularAcuities = new ElementValidationOcularAcuities(
          true, null, null
      );
    }

    @Test
    void shallReturnValidationErrorIfRightEyeIsMissingWithoutCorrection() {
      final var validationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
          .categoryId(CATEGORY_ID.orElse(null))
          .message(
              new ErrorMessage("Ange ett svar.")
          )
          .build();

      final var expectedValidationErrors = List.of(validationError);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallReturnValidationErrorIfLeftEyeIsMissingWithoutCorrection() {
      final var validationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
          .categoryId(CATEGORY_ID.orElse(null))
          .message(
              new ErrorMessage("Ange ett svar.")
          )
          .build();

      final var expectedValidationErrors = List.of(validationError);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallReturnValidationErrorIfBinocularIsMissingWithoutCorrection() {
      final var validationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
          .categoryId(CATEGORY_ID.orElse(null))
          .message(
              new ErrorMessage("Ange ett svar.")
          )
          .build();

      final var expectedValidationErrors = List.of(validationError);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }
  }

  @Nested
  class NotMandatoryTests {

    @BeforeEach
    void setUp() {
      elementValidationOcularAcuities = new ElementValidationOcularAcuities(
          false, null, null
      );
    }

    @Test
    void shallNotReturnValidationErrorIfRightEyeIsMissingWithoutCorrectionIfNotMandatory() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(Collections.emptyList(), actualValidationErrors);
    }

    @Test
    void shallNotReturnValidationErrorIfLeftEyeIsMissingWithoutCorrectionIfNotMandatory() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(Collections.emptyList(), actualValidationErrors);
    }

    @Test
    void shallNotReturnValidationErrorIfBinocularIsMissingWithoutCorrectionIfNotMandatory() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(Collections.emptyList(), actualValidationErrors);
    }
  }

  @Nested
  class MaxValueTest {

    @BeforeEach
    void setUp() {
      elementValidationOcularAcuities = new ElementValidationOcularAcuities(
          false, 0.0, 2.0
      );
    }

    @Test
    void shallReturnValidationErrorIfRightEyeWithoutCorrectionValueExceedsMaxLimit() {
      final var validationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
          .categoryId(CATEGORY_ID.orElse(null))
          .message(
              new ErrorMessage("Ange synskärpa i intervallet 0,0 - 2,0.")
          )
          .build();

      final var expectedValidationErrors = List.of(validationError);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallReturnValidationErrorIfRightEyeWithCorrectionValueExceedsMaxLimit() {
      final var validationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
          .categoryId(CATEGORY_ID.orElse(null))
          .message(
              new ErrorMessage("Ange synskärpa i intervallet 0,0 - 2,0.")
          )
          .build();

      final var expectedValidationErrors = List.of(validationError);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withCorrection(
                              WithCorrection.builder()
                                  .id(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallReturnValidationErrorIfLeftEyeWithoutCorrectionValueExceedsMaxLimit() {
      final var validationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
          .categoryId(CATEGORY_ID.orElse(null))
          .message(
              new ErrorMessage("Ange synskärpa i intervallet 0,0 - 2,0.")
          )
          .build();

      final var expectedValidationErrors = List.of(validationError);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .leftEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallReturnValidationErrorIfLeftEyeWithCorrectionValueExceedsMaxLimit() {
      final var validationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
          .categoryId(CATEGORY_ID.orElse(null))
          .message(
              new ErrorMessage("Ange synskärpa i intervallet 0,0 - 2,0.")
          )
          .build();

      final var expectedValidationErrors = List.of(validationError);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .leftEye(
                      OcularAcuity.builder()
                          .withCorrection(
                              WithCorrection.builder()
                                  .id(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallReturnValidationErrorIfBinocularWithoutCorrectionValueExceedsMaxLimit() {
      final var validationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
          .categoryId(CATEGORY_ID.orElse(null))
          .message(
              new ErrorMessage("Ange synskärpa i intervallet 0,0 - 2,0.")
          )
          .build();

      final var expectedValidationErrors = List.of(validationError);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .binocular(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              WithoutCorrection.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallReturnValidationErrorIfBinocularWithCorrectionValueExceedsMaxLimit() {
      final var validationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(new FieldId(BINOCULAR_WITH_CORRECTION_ID))
          .categoryId(CATEGORY_ID.orElse(null))
          .message(
              new ErrorMessage("Ange synskärpa i intervallet 0,0 - 2,0.")
          )
          .build();

      final var expectedValidationErrors = List.of(validationError);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueOcularAcuities.builder()
                  .binocular(
                      OcularAcuity.builder()
                          .withCorrection(
                              WithCorrection.builder()
                                  .id(new FieldId(BINOCULAR_WITH_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationOcularAcuities.validate(
          elementData,
          CATEGORY_ID
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }
  }
}
