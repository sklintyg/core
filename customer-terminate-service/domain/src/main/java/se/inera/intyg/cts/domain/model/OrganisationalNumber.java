package se.inera.intyg.cts.domain.model;

public record OrganisationalNumber(String number) {

  public OrganisationalNumber {
    if (number == null || number.isBlank()) {
      throw new IllegalArgumentException("Missing OrganizationalNumber");
    }
  }
}
