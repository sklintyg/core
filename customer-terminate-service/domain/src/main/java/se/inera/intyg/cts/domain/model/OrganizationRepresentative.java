package se.inera.intyg.cts.domain.model;

public record OrganizationRepresentative(PersonId personId, PhoneNumber phoneNumber) {

  public OrganizationRepresentative {
    if (personId == null) {
      throw new IllegalArgumentException("Missing PersonId");
    }
    if (phoneNumber == null) {
      throw new IllegalArgumentException("Missing PhoneNumber");
    }
  }
}
