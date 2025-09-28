package se.inera.intyg.certificateanalyticsservice.application.messages.model.common;

import lombok.Getter;

@Getter
public enum RoleType {
  DOCTOR(1),
  NURSE(2),
  MID_WIFE(3),
  CARE_ADMIN(4),
  PATIENT(5),
  CERTIFICATE_RECIPIENT(6);

  private final int key;

  RoleType(int key) {
    this.key = key;
  }

}

