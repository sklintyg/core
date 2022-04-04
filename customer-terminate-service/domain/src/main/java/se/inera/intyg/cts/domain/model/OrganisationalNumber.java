package se.inera.intyg.cts.domain.model;

import java.util.Objects;

public record OrganisationalNumber(String number) {

  public OrganisationalNumber {
    Objects.requireNonNull(number, "Missing OrganizationalNumber");
  }
}
