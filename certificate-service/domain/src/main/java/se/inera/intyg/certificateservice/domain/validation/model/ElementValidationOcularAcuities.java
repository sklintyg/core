package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationOcularAcuities implements ElementValidation {

  boolean mandatory;
  Double min;
  Double max;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var value = getValue(data.value());
    final var validationErrors = new ArrayList<ValidationError>();
    if (mandatory) {
      validationErrors.addAll(validateMissingValue(value, data, categoryId));
    }
    return validationErrors;
  }

  private Collection<ValidationError> validateMissingValue(
      ElementValueOcularAcuities ocularAcuities, ElementData data, Optional<ElementId> categoryId) {
    final var validationErrors = new ArrayList<ValidationError>();
    if (ocularAcuities.rightEye().withoutCorrection().value() == null) {
      validationErrors.add(
          buildValidationError(
              ocularAcuities.rightEye().withoutCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.missingAnswer().value()
          )
      );
    }

    if (ocularAcuities.leftEye().withoutCorrection().value() == null) {
      validationErrors.add(
          buildValidationError(
              ocularAcuities.leftEye().withoutCorrection().id(),
              data,
              categoryId,
              ErrorMessageFactory.missingAnswer().value()
          )
      );
    }

    if (ocularAcuities.binocular().withoutCorrection().value() == null) {
      validationErrors.add(
          buildValidationError(
              ocularAcuities.binocular().withoutCorrection().id(),
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

  private ElementValueOcularAcuities getValue(ElementValue value) {
    if (value == null) {
      throw new IllegalArgumentException("Element data value is null");
    }

    if (value instanceof ElementValueOcularAcuities ocularAcuities) {
      return ocularAcuities;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }

}
