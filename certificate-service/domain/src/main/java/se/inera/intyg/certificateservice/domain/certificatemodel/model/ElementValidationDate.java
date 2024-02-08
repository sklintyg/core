package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationDate implements ElementValidation {

  boolean mandatory;
  TemporalAmount min;
  TemporalAmount max;

  @Override
  public boolean validate(ElementData data) {
    return elementValueDate(data)
        .map(date -> date.isAfter(minDate()) && date.isBefore(maxDate()))
        .orElse(!mandatory);
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
    return LocalDate.now(ZoneId.systemDefault()).minus(min).minusDays(1);
  }

  private LocalDate maxDate() {
    return LocalDate.now(ZoneId.systemDefault()).plus(max).plusDays(1);
  }
}
