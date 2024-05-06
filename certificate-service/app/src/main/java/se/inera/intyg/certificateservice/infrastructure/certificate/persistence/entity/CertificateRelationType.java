package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import lombok.Getter;

@Getter
public enum CertificateRelationType {
  REPLACE(1), RENEW(2);

  private final int key;

  CertificateRelationType(int key) {
    this.key = key;
  }
}
