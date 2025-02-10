package se.inera.intyg.certificateservice.domain.common.model;

public record HsaId(String id) {

  public static final String OID = "1.2.752.129.2.1.4.1";

  public static HsaId create(String id) {
    return new HsaId(id);
  }

  public HsaId {
    if (id == null || id.isBlank()) {
      throw new IllegalArgumentException("Missing id");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof HsaId(String otherId)) {
      return id.equalsIgnoreCase(otherId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return id.toUpperCase().hashCode();
  }
}
