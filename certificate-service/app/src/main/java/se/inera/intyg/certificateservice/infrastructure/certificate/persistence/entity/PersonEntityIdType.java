package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

public enum PersonEntityIdType {
  PERSONAL_IDENTITY_NUMBER(1), COORDINATION_NUMBER(2);

  private int key;

  PersonEntityIdType(int key) {
    this.key = key;
  }

  public int getKey() {
    return this.key;
  }
}
