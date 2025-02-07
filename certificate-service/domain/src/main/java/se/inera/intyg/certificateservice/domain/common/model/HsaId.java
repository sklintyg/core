package se.inera.intyg.certificateservice.domain.common.model;

public record HsaId(String id) {

  public static final String OID = "1.2.752.129.2.1.4.1";

  public HsaId(String id) {
    if (id == null || id.isBlank()) {
      throw new IllegalArgumentException("Missing id");
    }
    this.id = id.toUpperCase();
  }
}
