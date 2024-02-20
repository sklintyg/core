package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
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
    if (data.value() instanceof ElementValueDate dateValue) {
      if (dateValue.date() == null) {
        return errorMessage(data, dateValue, categoryId, "Ange ett datum.");
      } else if (dateValue.date().isBefore(minDate())) {
        return errorMessage(data, dateValue, categoryId,
            "Ange ett datum som är tidigast %s.".formatted(minDate()));
      } else if (dateValue.date().isAfter(maxDate())) {
        return errorMessage(data, dateValue, categoryId,
            "Ange ett datum som är senast %s.".formatted(maxDate()));
      }
    }
    return null;
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

  private static Optional<LocalDate> elementValueDate(ElementData data) {
    if (data == null || data.value() == null) {
      return Optional.empty();
    }
    if (data.value() instanceof ElementValueDate date) {
      return Optional.ofNullable(date.date());
    }
    return Optional.empty();
  }

  private LocalDate minDate() {
    return LocalDate.now(ZoneId.systemDefault()).minus(min);
  }

  private LocalDate maxDate() {
    return LocalDate.now(ZoneId.systemDefault()).plus(max);
  }

}
