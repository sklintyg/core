package se.inera.intyg.certificateservice.domain.validation.model;

import java.time.LocalDate;

public class ErrorMessageFactory {

  private ErrorMessageFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static ErrorMessage maxDate(LocalDate maxDate) {
    return new ErrorMessage("Ange ett datum som är senast %s.".formatted(maxDate));
  }

  public static ErrorMessage minDate(LocalDate minDate) {
    return new ErrorMessage("Ange ett datum som är tidigast %s.".formatted(minDate));
  }

  public static ErrorMessage textLimit(Integer limit) {
    return new ErrorMessage("Ange en text som inte är längre än %s.".formatted(limit));
  }

  public static ErrorMessage missingDate() {
    return new ErrorMessage("Ange ett datum.");
  }

  public static ErrorMessage missingText() {
    return new ErrorMessage("Ange ett svar.");
  }

  public static ErrorMessage missingMultipleOption() {
    return new ErrorMessage("Välj minst ett alternativ.");
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
}
