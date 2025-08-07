package se.inera.intyg.certificateservice.domain.validation.model;

import java.time.temporal.TemporalAmount;

public class ErrorMessageFactory {

  private ErrorMessageFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static ErrorMessage maxDate(TemporalAmount max) {
    return new ErrorMessage("Ange ett datum som är senast %s.".formatted(
        ElementValidator.toDateFromTemporalAmount(max)));
  }

  public static ErrorMessage minDate(TemporalAmount min) {
    return new ErrorMessage("Ange ett datum som är tidigast %s.".formatted(
        ElementValidator.toDateFromTemporalAmount(min)));
  }

  public static ErrorMessage textLimit(Integer limit) {
    return new ErrorMessage("Ange en text som inte är längre än %s.".formatted(limit));
  }

  public static ErrorMessage integerInterval(Integer min, Integer max) {
    return new ErrorMessage("Ange ett värde mellan %s-%s.".formatted(min, max));
  }

  public static ErrorMessage missingDate() {
    return new ErrorMessage("Ange ett datum.");
  }

  public static ErrorMessage missingAnswer() {
    return new ErrorMessage("Ange ett svar.");
  }

  public static ErrorMessage missingMultipleOption() {
    return new ErrorMessage("Välj minst ett alternativ.");
  }

  public static ErrorMessage missingDateRange() {
    return new ErrorMessage("Ange period.");
  }

  public static ErrorMessage missingOption() {
    return new ErrorMessage("Välj ett alternativ.");
  }

  public static ErrorMessage overlappingPeriods() {
    return new ErrorMessage("Ange perioder som inte överlappar varandra.");
  }

  public static ErrorMessage endDateAfterStartDate() {
    return new ErrorMessage("Ange ett slutdatum som infaller efter startdatumet.");
  }

  public static ErrorMessage fieldOrderDescending() {
    return new ErrorMessage("Fyll i fälten uppifrån och ned.");
  }

  public static ErrorMessage visualAcuityOutsideInterval(Double min, Double max) {
    final var interval = "%s - %s".formatted(min, max).replace(".", ",");
    return new ErrorMessage("Ange synskärpa i intervallet %s.".formatted(interval));
  }
}