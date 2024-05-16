package se.inera.intyg.certificateservice.domain.certificate.model;

public enum RelationType {
  REPLACE, RENEW;

  public String toRelationKod() {
    return switch (this) {
      case REPLACE -> "ERSATT";
      case RENEW -> "FRLANG";
    };
  }

  public String toRelationKodText() {
    return switch (this) {
      case REPLACE -> "Ersätter";
      case RENEW -> "Förlänger";
    };
  }
}
