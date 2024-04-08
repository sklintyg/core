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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationDateRange implements ElementValidation {

  boolean mandatory;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var dateRange = getValue(data.value());

    if (mandatory && !isDateRangeComplete(dateRange)) {
      return errorMessage(data, dateRange, categoryId, "Ange ett datum.");
    }

    return Collections.emptyList();
  }

  private ElementValueDateRange getValue(ElementValue value) {
    if (value == null) {
      throw new IllegalArgumentException("Element data value is null");
    }

    if (value instanceof ElementValueDateRange dateRangeValue) {
      return dateRangeValue;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }

  private boolean isDateRangeComplete(ElementValueDateRange value) {
    return value.from() != null
        && value.to() != null;
  }

  private static List<ValidationError> errorMessage(ElementData data,
      ElementValueDateRange dateValue,
      Optional<ElementId> categoryId, String message) {
    return List.of(
        ValidationError.builder()
            .elementId(data.id())
            .fieldId(dateValue.dateRangeId())
            .categoryId(categoryId.orElse(null))
            .message(new ErrorMessage(message))
            .build()
    );
  }

}
