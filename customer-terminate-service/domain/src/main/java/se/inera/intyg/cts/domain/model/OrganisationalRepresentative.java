package se.inera.intyg.cts.domain.model;

import java.util.Objects;

public record OrganisationalRepresentative(PersonId personId, PhoneNumber phoneNumber) {

  public OrganisationalRepresentative {
    Objects.requireNonNull(personId);
    Objects.requireNonNull(phoneNumber);
  }
}
