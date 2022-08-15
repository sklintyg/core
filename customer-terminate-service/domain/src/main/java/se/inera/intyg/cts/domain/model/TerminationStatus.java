package se.inera.intyg.cts.domain.model;

public enum TerminationStatus {
  CREATED("Skapad"),
  COLLECTING_CERTIFICATES("Hämtar intyg"),
  COLLECTING_CERTIFICATES_COMPLETED("Intyg hämtade"),
  COLLECTING_CERTIFICATE_TEXTS_COMPLETED("Intygstexter hämtade"),
  EXPORTED("Uppladdat"),
  NOTIFICATION_SENT("Notifiering skickad"),
  REMINDER_SENT("Påminnelse skickad"),
  RECEIPT_RECEIVED("Kvitterad"),
  PASSWORD_SENT("Kryptonyckel skickad"),
  START_ERASE("Starta radering"),
  ERASE_IN_PROGRESS("Radering pågår"),
  ERASE_CANCELLED("Radering avbruten"),
  ERASE_COMPLETED("Radering utförd");

  private final String description;

  TerminationStatus(String description) {
    this.description = description;
  }

  public String description() {
    return description;
  }
}
