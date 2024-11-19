package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

public enum StaffRole {
  CARE_ADMIN(1),
  DOCTOR(2),
  PRIVATE_DOCTOR(3),
  DENTIST(4),
  NURSE(5),
  MIDWIFE(6);

  private final int key;

  StaffRole(int key) {
    this.key = key;
  }

  public int getKey() {
    return key;
  }
}
