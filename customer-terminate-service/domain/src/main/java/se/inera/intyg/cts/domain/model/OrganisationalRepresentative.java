package se.inera.intyg.cts.domain.model;

public record OrganisationalRepresentative(PersonId personId, PhoneNumber phoneNumber) {

  public OrganisationalRepresentative {
    if (personId == null) {
      throw new IllegalArgumentException("Missing PersonId");
    }
    if (phoneNumber == null) {
      throw new IllegalArgumentException("Missing PhoneNumber");
    }
  }
}
