package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Getter
@Builder
@Slf4j
public class ElementValidationVisualAcuities implements ElementValidation {

  boolean mandatory;
  Double min;
  Double max;
  Predicate<List<ElementData>> shouldValidateSightOnBothEyes;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId, List<ElementData> elementData) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var value = getValue(data.value());
    final var validationErrors = new ArrayList<ValidationError>();
    if (mandatory) {
      validationErrors.addAll(validateMissingValue(value, data, categoryId));
    }

    if (min != null && max != null) {
      validationErrors.addAll(validateMinAndMaxValues(value, data, categoryId));
    }

    if (shouldValidateSightOnBothEyes != null && shouldValidateSightOnBothEyes.test(elementData)) {
      validationErrors.addAll(validateHasSightOnBothEyes(value, data, categoryId));
    }

    return validationErrors;
  }

  private Collection<ValidationError> validateHasSightOnBothEyes(
      ElementValueVisualAcuities visualAcuities, ElementData data, Optional<ElementId> categoryId) {
    final var validationErrors = new ArrayList<ValidationError>();
    if (!valueIsZero(visualAcuities.rightEye().withoutCorrection()) && !valueIsZero(
        visualAcuities.leftEye().withoutCorrection())) {
      return validationErrors;
    }

    if (missingValue(visualAcuities.rightEye().withCorrection())) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.rightEye().withCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.missingAnswer().value()
          )
      );
    }

    if (missingValue(visualAcuities.leftEye().withCorrection())) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.leftEye().withCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.missingAnswer().value()
          )
      );
    }

    if (missingValue(visualAcuities.binocular().withCorrection())) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.binocular().withCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.missingAnswer().value()
          )
      );
    }

    return validationErrors;
  }

  private boolean valueIsZero(Correction correction) {
    return correction != null && correction.value() != null && correction.value() == 0.0;
  }

  private boolean missingValue(Correction correction) {
    return correction != null && correction.value() == null;
  }

  private Collection<ValidationError> validateMinAndMaxValues(
      ElementValueVisualAcuities visualAcuities, ElementData data, Optional<ElementId> categoryId) {
    final var validationErrors = new ArrayList<ValidationError>();

    if (outsideOfAllowedInterval(visualAcuities.rightEye().withCorrection())) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.rightEye().withCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.visualAcuityOutsideInterval(min, max).value()
          )
      );
    }

    if (outsideOfAllowedInterval(visualAcuities.rightEye().withoutCorrection())) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.rightEye().withoutCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.visualAcuityOutsideInterval(min, max).value()
          )
      );
    }

    if (outsideOfAllowedInterval(visualAcuities.leftEye().withCorrection())) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.leftEye().withCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.visualAcuityOutsideInterval(min, max).value()
          )
      );
    }

    if (outsideOfAllowedInterval(visualAcuities.leftEye().withoutCorrection())) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.leftEye().withoutCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.visualAcuityOutsideInterval(min, max).value()
          )
      );
    }

    if (outsideOfAllowedInterval(visualAcuities.binocular().withCorrection())) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.binocular().withCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.visualAcuityOutsideInterval(min, max).value()
          )
      );
    }

    if (outsideOfAllowedInterval(visualAcuities.binocular().withoutCorrection())) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.binocular().withoutCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.visualAcuityOutsideInterval(min, max).value()
          )
      );
    }

    return validationErrors;
  }

  private boolean outsideOfAllowedInterval(Correction correction) {
    return correction != null && correction.value() != null && (correction.value() < min
        || correction.value() > max);
  }

  private Collection<ValidationError> validateMissingValue(
      ElementValueVisualAcuities visualAcuities, ElementData data, Optional<ElementId> categoryId) {
    final var validationErrors = new ArrayList<ValidationError>();
    if (visualAcuities.rightEye().withoutCorrection().value() == null) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.rightEye().withoutCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.missingAnswer().value()
          )
      );
    }

    if (visualAcuities.leftEye().withoutCorrection().value() == null) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.leftEye().withoutCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.missingAnswer().value()
          )
      );
    }

    if (visualAcuities.binocular().withoutCorrection().value() == null) {
      validationErrors.add(
          buildValidationError(
              visualAcuities.binocular().withoutCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.missingAnswer().value()
          )
      );
    }

    return validationErrors;
  }

  private static ValidationError buildValidationError(
      FieldId fieldId,
      ElementData data, Optional<ElementId> categoryId, String errorMessage) {
    return ValidationError.builder()
        .elementId(data.id())
        .fieldId(fieldId)
        .categoryId(categoryId.orElse(null))
        .message(new ErrorMessage(errorMessage))
        .build();
  }

  private ElementValueVisualAcuities getValue(ElementValue value) {
    if (value == null) {
      throw new IllegalArgumentException("Element data value is null");
    }

    if (value instanceof ElementValueVisualAcuities visualAcuities) {
      return visualAcuities;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }

}