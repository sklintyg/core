package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

public enum StaffRole {
  CARE_ADMIN(1), DOCTOR(2), DENTIST(3), NURSE(4), MIDWIFE(5);

  private final int key;

  StaffRole(int key) {
    this.key = key;
  }

  public int getKey() {
    return key;
  }
}
