package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import lombok.Getter;

@Getter
public enum RevokedReason {
  INCORRECT_PATIENT(1), OTHER_SERIOUS_ERROR(2);

  private final int key;

  RevokedReason(int key) {
    this.key = key;
  }

}
