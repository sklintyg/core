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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

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
      return List.of(
          errorMessage(data, value.textId(), categoryId, "Ange ett svar.")
      );
    }

    if (value.text() != null && isWithinLimit(value.text())) {
      return List.of(
          errorMessage(data, value.textId(), categoryId,
              "Ange en text som inte är längre än %s.".formatted(limit)
          )
      );
    }

    return Collections.emptyList();
  }

  private static ValidationError errorMessage(
      ElementData data,
      FieldId fieldId,
      Optional<ElementId> categoryId,
      String message) {
    return ValidationError.builder()
        .elementId(data.id())
        .fieldId(fieldId)
        .categoryId(categoryId.orElse(null))
        .message(new ErrorMessage(message))
        .build();
  }

  private boolean isWithinLimit(String value) {
    return limit != null && value.length() > limit;
  }

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

}
