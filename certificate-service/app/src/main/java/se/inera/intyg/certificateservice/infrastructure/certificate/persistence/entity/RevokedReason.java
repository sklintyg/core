package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import lombok.Getter;

@Getter
public enum RevokedReason {
  FEL_PATIENT(1), ANNAT_ALLVARLIGT_FEL(2);

  private final int key;

  RevokedReason(int key) {
    this.key = key;
  }

}
