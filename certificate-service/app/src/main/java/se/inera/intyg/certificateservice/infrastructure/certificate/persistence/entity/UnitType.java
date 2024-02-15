package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

public enum UnitType {
  CARE_PROVIDER(1), CARE_UNIT(2), SUB_UNIT(3);

  private final int key;

  UnitType(int key) {
    this.key = key;
  }

  public int getKey() {
    return key;
  }
}
