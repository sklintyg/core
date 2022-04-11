package se.inera.intyg.cts.domain.model;

public enum TerminationStatus {
  CREATED("Skapad");

  private final String description;

  TerminationStatus(String description) {
    this.description = description;
  }

  public String description() {
    return description;
  }
}
