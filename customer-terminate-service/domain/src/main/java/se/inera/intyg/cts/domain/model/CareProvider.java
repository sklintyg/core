package se.inera.intyg.cts.domain.model;

public record CareProvider(HSAId hsaId, OrganisationalNumber organisationalNumber) {

  public CareProvider {
    if (hsaId == null) {
      throw new IllegalArgumentException("Missing HSAId");
    }
    if (organisationalNumber == null) {
      throw new IllegalArgumentException("Missing OrganizationalNumber");
    }
  }
}
