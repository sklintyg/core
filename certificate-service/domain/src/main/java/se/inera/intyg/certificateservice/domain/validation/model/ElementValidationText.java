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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationText implements ElementValidation {

  boolean mandatory;
  Integer limit;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var value = getValue(data.value());

    if (mandatory && value.text() == null) {
      return errorMessage(data, value, categoryId, "Ange ett svar.");
    }

    if (value.text() != null && isWithinLimit(value.text())) {
      return errorMessage(data, value, categoryId,
          "Ange en text som inte är längre än %s.".formatted(limit)
      );
    }

    return Collections.emptyList();
  }

  private boolean isWithinLimit(String value) {
    return limit != null && value.length() > limit;
  }

  // TODO: Extract to common logic so not duplicated between validation date and text

  private ElementValueText getValue(ElementValue value) {
    if (value == null) {
      throw new IllegalArgumentException("Element data value is null");
    }

    if (value instanceof ElementValueText dateValue) {
      return dateValue;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }

  // TODO: Extract to common logic so not duplicated between validation date and text

  private static List<ValidationError> errorMessage(ElementData data, ElementValueText value,
      Optional<ElementId> categoryId, String message) {
    return List.of(
        ValidationError.builder()
            .elementId(data.id())
            .fieldId(value.textId())
            .categoryId(categoryId.orElse(null))
            .message(new ErrorMessage(message))
            .build()
    );
  }

}
