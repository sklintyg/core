package se.inera.intyg.cts.domain.model;

import java.util.Objects;

public record CareProvider(HSAId hsaId, OrganisationalNumber organisationalNumber) {

  public CareProvider {
    Objects.requireNonNull(hsaId);
    Objects.requireNonNull(organisationalNumber);
  }
}
