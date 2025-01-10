package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationVisualAcuitiesTest {

  private static final Optional<ElementId> CATEGORY_ID = Optional.of(new ElementId("id"));
  private static final String LEFT_EYE_WITHOUT_CORRECTION_ID = "LEFT_EYE_WITHOUT_CORRECTION_ID";
  private static final String RIGHT_EYE_WITHOUT_CORRECTION_ID = "RIGHT_EYE_WITHOUT_CORRECTION_ID";
  private static final String BINOCULAR_WITHOUT_CORRECTION_ID = "BINOCULAR_WITHOUT_CORRECTION_ID";
  private static final String LEFT_EYE_WITH_CORRECTION_ID = "LEFT_EYE_WITH_CORRECTION_ID";
  private static final String RIGHT_EYE_WITH_CORRECTION_ID = "RIGHT_EYE_WITH_CORRECTION_ID";
  private static final String BINOCULAR_WITH_CORRECTION_ID = "BINOCULAR_WITH_CORRECTION_ID";
  private static final double VALUE = 1.0;
  private static final ElementId ELEMENT_ID = new ElementId("1.1");
  ElementValidationVisualAcuities elementValidationVisualAcuities;

  @Nested
  class ValidateElementData {

    @BeforeEach
    void setUp() {
      elementValidationVisualAcuities = new ElementValidationVisualAcuities(
          true, 0.0, 2.0, null, null
      );
    }

    @Test
    void shallThrowIfElementDataIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> elementValidationVisualAcuities.validate(null, CATEGORY_ID,
              Collections.emptyList()));

      assertEquals("Element data is null", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfElementDataValueIsNull() {
      final var elementData = ElementData.builder().build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> elementValidationVisualAcuities.validate(elementData, CATEGORY_ID,
              Collections.emptyList()));

      assertEquals("Element data value is null", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfElementDataValueIsWrongType() {
      final var elementValueText = ElementValueText.builder().build();
      final var elementData = ElementData.builder()
          .value(elementValueText)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> elementValidationVisualAcuities.validate(elementData, CATEGORY_ID,
              Collections.emptyList()));

      assertEquals("Element data value %s is of wrong type".formatted(elementValueText.getClass()),
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class MandatoryTests {

    @BeforeEach
    void setUp() {
      elementValidationVisualAcuities = new ElementValidationVisualAcuities(
          true, null, null, null, null
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
              ElementValueVisualAcuities.builder()
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

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
              ElementValueVisualAcuities.builder()
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

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
              ElementValueVisualAcuities.builder()
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }
  }

  @Nested
  class NotMandatoryTests {

    @BeforeEach
    void setUp() {
      elementValidationVisualAcuities = new ElementValidationVisualAcuities(
          false, null, null, null, null
      );
    }

    @Test
    void shallNotReturnValidationErrorIfRightEyeIsMissingWithoutCorrectionIfNotMandatory() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueVisualAcuities.builder()
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualValidationErrors);
    }

    @Test
    void shallNotReturnValidationErrorIfLeftEyeIsMissingWithoutCorrectionIfNotMandatory() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueVisualAcuities.builder()
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualValidationErrors);
    }

    @Test
    void shallNotReturnValidationErrorIfBinocularIsMissingWithoutCorrectionIfNotMandatory() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueVisualAcuities.builder()
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(VALUE)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualValidationErrors);
    }
  }

  @Nested
  class MaxValueTest {

    @BeforeEach
    void setUp() {
      elementValidationVisualAcuities = new ElementValidationVisualAcuities(
          false, 0.0, 2.0, null, null
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
              ElementValueVisualAcuities.builder()
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(getDefaultVisualActuity())
                  .binocular(getDefaultVisualActuity())
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

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
              ElementValueVisualAcuities.builder()
                  .rightEye(
                      VisualAcuity.builder()
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(getDefaultVisualActuity())
                  .binocular(getDefaultVisualActuity())
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

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
              ElementValueVisualAcuities.builder()
                  .rightEye(getDefaultVisualActuity()
                  )
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(getDefaultVisualActuity())
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

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
              ElementValueVisualAcuities.builder()
                  .rightEye(getDefaultVisualActuity())
                  .leftEye(
                      VisualAcuity.builder()
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(getDefaultVisualActuity())
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

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
              ElementValueVisualAcuities.builder()
                  .rightEye(getDefaultVisualActuity())
                  .leftEye(getDefaultVisualActuity())
                  .binocular(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

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
              ElementValueVisualAcuities.builder()
                  .rightEye(getDefaultVisualActuity())
                  .leftEye(getDefaultVisualActuity())
                  .binocular(
                      VisualAcuity.builder()
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(BINOCULAR_WITH_CORRECTION_ID))
                                  .value(3.0)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList());

      assertEquals(expectedValidationErrors, actualValidationErrors);
    }
  }

  @Nested
  class MinAllowedSightTests {


    @BeforeEach
    void setUp() {
      elementValidationVisualAcuities = new ElementValidationVisualAcuities(
          false, 0.0, 2.0, 0.1, 0.8);
    }

    @Test
    void shallReturnValidationErrorIfBothEyesAreUnderMinimalLimit() {
      final var expectedValidationErrors = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
              .categoryId(CATEGORY_ID.orElse(null))
              .message(
                  new ErrorMessage("Ange ett svar.")
              )
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
              .categoryId(CATEGORY_ID.orElse(null))
              .message(
                  new ErrorMessage("Ange ett svar.")
              )
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(BINOCULAR_WITH_CORRECTION_ID))
              .categoryId(CATEGORY_ID.orElse(null))
              .message(
                  new ErrorMessage("Ange ett svar.")
              )
              .build()
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueVisualAcuities.builder()
                  .binocular(getDefaultVisualActuityWithId(BINOCULAR_WITHOUT_CORRECTION_ID,
                      BINOCULAR_WITH_CORRECTION_ID))
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(0.7)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(0.0)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList()
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);

    }

    @Test
    void shallReturnValidationErrorIfRightEyeIsUnderMinimalLimitAndLeftEyeIsWithinAllowedLimit() {
      final var expectedValidationErrors = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
              .categoryId(CATEGORY_ID.orElse(null))
              .message(
                  new ErrorMessage("Ange ett svar.")
              )
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
              .categoryId(CATEGORY_ID.orElse(null))
              .message(
                  new ErrorMessage("Ange ett svar.")
              )
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(BINOCULAR_WITH_CORRECTION_ID))
              .categoryId(CATEGORY_ID.orElse(null))
              .message(
                  new ErrorMessage("Ange ett svar.")
              )
              .build()
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueVisualAcuities.builder()
                  .binocular(getDefaultVisualActuityWithId(BINOCULAR_WITHOUT_CORRECTION_ID,
                      BINOCULAR_WITH_CORRECTION_ID))
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(1.7)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(0.0)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList()
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);

    }

    @Test
    void shallReturnValidationErrorIfLeftEyeIsUnderMinimalLimitAndRightEyeIsWithinAllowedLimit() {
      final var expectedValidationErrors = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
              .categoryId(CATEGORY_ID.orElse(null))
              .message(
                  new ErrorMessage("Ange ett svar.")
              )
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
              .categoryId(CATEGORY_ID.orElse(null))
              .message(
                  new ErrorMessage("Ange ett svar.")
              )
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(BINOCULAR_WITH_CORRECTION_ID))
              .categoryId(CATEGORY_ID.orElse(null))
              .message(
                  new ErrorMessage("Ange ett svar.")
              )
              .build()
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueVisualAcuities.builder()
                  .binocular(getDefaultVisualActuityWithId(BINOCULAR_WITHOUT_CORRECTION_ID,
                      BINOCULAR_WITH_CORRECTION_ID))
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(0.0)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(1.3)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualValidationErrors = elementValidationVisualAcuities.validate(
          elementData,
          CATEGORY_ID,
          Collections.emptyList()
      );

      assertEquals(expectedValidationErrors, actualValidationErrors);

    }
  }

  private static VisualAcuity getDefaultVisualActuity() {
    return VisualAcuity.builder()
        .withoutCorrection(Correction.builder()
            .id(new FieldId("ID1"))
            .value(1.0)
            .build()
        )
        .withCorrection(Correction.builder()
            .id(new FieldId("ID2"))
            .value(2.0)
            .build()
        )
        .build();
  }

  private static VisualAcuity getDefaultVisualActuityWithId(String withoutId, String withId) {
    return VisualAcuity.builder()
        .withoutCorrection(Correction.builder()
            .id(new FieldId(withoutId))
            .value(1.0)
            .build()
        )
        .withCorrection(Correction.builder()
            .id(new FieldId(withId))
            .build()
        )
        .build();
  }
}