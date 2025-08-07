package se.inera.intyg.certificateservice.domain.validation.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;

public class ElementValidator {

  private ElementValidator() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean isTextOverLimit(String value, Integer limit) {
    return limit != null && value != null && value.length() > limit;
  }

  public static boolean isIntegerWithinLimit(Integer value, Integer min, Integer max) {
    if (min == null && max == null) {
      return true;
    }

    if (min == null) {
      return value != null && value <= max;
    }

    if (max == null) {
      return value != null && value >= min;
    }

    return value != null && value >= min && value <= max;
  }

  public static boolean isDateAfterMax(LocalDate value, TemporalAmount max) {
    return value != null
        && max != null
        && value.isAfter(ElementValidator.toDateFromTemporalAmount(max));
  }

  public static boolean isDateBeforeMin(LocalDate value, TemporalAmount min) {
    return value != null
        && min != null
        && value.isBefore(ElementValidator.toDateFromTemporalAmount(min));
  }

  public static boolean isTextDefined(String text) {
    return text != null && !text.isEmpty() && !text.isBlank();
  }

  public static LocalDate toDateFromTemporalAmount(TemporalAmount limit) {
    return LocalDate.now(ZoneId.systemDefault()).plus(limit);
  }
}
