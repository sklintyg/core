package se.inera.intyg.certificateservice.domain.common.model;

public enum PersonIdType {
  PERSONAL_IDENTITY_NUMBER("1.2.752.129.2.1.3.1"),
  COORDINATION_NUMBER("1.2.752.129.2.1.3.3");

  private final String oid;

  PersonIdType(String oid) {
    this.oid = oid;
  }

  public String oid() {
    return oid;
  }
}
