package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

public enum UnitType {
  SUB_UNIT(1), CARE_UNIT(2), CARE_PROVIDER(3);

  private final int key;

  UnitType(int key) {
    this.key = key;
  }

  public int getKey() {
    return key;
  }
}
