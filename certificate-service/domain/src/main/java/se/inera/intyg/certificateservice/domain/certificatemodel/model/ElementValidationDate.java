package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.validation.model.ErrorMessage;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationError;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationDate implements ElementValidation {

  boolean mandatory;
  TemporalAmount min;
  TemporalAmount max;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var dateValue = getValue(data.value());

    if (mandatory && dateValue.date() == null) {
      return errorMessage(data, dateValue, categoryId, "Ange ett datum.");
    }

    if (isDateBeforeMin(dateValue)) {
      return errorMessage(data, dateValue, categoryId,
          "Ange ett datum som är tidigast %s.".formatted(minDate()));
    }

    if (isDateAfterMax(dateValue)) {
      return errorMessage(data, dateValue, categoryId,
          "Ange ett datum som är senast %s.".formatted(maxDate()));
    }

    return Collections.emptyList();
  }

  private ElementValueDate getValue(ElementValue value) {
    if (value == null) {
      throw new IllegalArgumentException("Element data value is null");
    }

    if (value instanceof ElementValueDate dateValue) {
      return dateValue;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }

  private boolean isDateAfterMax(ElementValueDate dateValue) {
    return dateValue.date() != null && max != null && dateValue.date().isAfter(maxDate());
  }

  private boolean isDateBeforeMin(ElementValueDate dateValue) {
    return dateValue.date() != null && min != null && dateValue.date().isBefore(minDate());
  }

  private static List<ValidationError> errorMessage(ElementData data, ElementValueDate dateValue,
      Optional<ElementId> categoryId, String message) {
    return List.of(
        ValidationError.builder()
            .elementId(data.id())
            .fieldId(dateValue.dateId())
            .categoryId(categoryId.orElse(null))
            .message(new ErrorMessage(message))
            .build()
    );
  }

  private LocalDate minDate() {
    return LocalDate.now(ZoneId.systemDefault()).minus(min);
  }

  private LocalDate maxDate() {
    return LocalDate.now(ZoneId.systemDefault()).plus(max);
  }

}
