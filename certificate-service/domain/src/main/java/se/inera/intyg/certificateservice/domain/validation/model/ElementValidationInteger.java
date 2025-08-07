package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationInteger implements ElementValidation {

  boolean mandatory;
  Integer min;
  Integer max;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId, List<ElementData> dataList) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var value = getValue(data.value());

    if (mandatory && value.isEmpty()) {
      return List.of(
          errorMessage(data, value.integerId(), categoryId, ErrorMessageFactory.missingAnswer())
      );
    }

    if (value.value() != null && !ElementValidator.isIntegerWithinLimit(value.value(), min, max)) {
      return List.of(
          errorMessage(data, value.integerId(), categoryId,
              ErrorMessageFactory.integerInterval(min, max)
          )
      );
    }

    return Collections.emptyList();
  }

  private static ValidationError errorMessage(
      ElementData data,
      FieldId fieldId,
      Optional<ElementId> categoryId,
      ErrorMessage message) {
    return ValidationError.builder()
        .elementId(data.id())
        .fieldId(fieldId)
        .categoryId(categoryId.orElse(null))
        .message(message)
        .build();
  }

  private ElementValueInteger getValue(ElementValue value) {
    if (value == null) {
      throw new IllegalArgumentException("Element data value is null");
    }

    if (value instanceof ElementValueInteger integerValue) {
      return integerValue;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }

}