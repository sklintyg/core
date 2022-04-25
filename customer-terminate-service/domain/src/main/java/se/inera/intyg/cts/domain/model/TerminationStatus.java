package se.inera.intyg.cts.domain.model;

public enum TerminationStatus {
  CREATED("Skapad"),
  COLLECTING_CERTIFICATES("Hämtar intyg"),
  COLLECTING_CERTIFICATES_COMPLETED("Intyg hämtade"),
  COLLECTING_CERTIFICATE_TEXTS_COMPLETED("Intygstexter hämtade"),
  EXPORTED("Uppladdat");

  private final String description;

  TerminationStatus(String description) {
    this.description = description;
  }

  public String description() {
    return description;
  }
}
