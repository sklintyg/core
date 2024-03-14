package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

public enum CertificateStatus {
  DRAFT(1), SIGNED(2);

  private final int key;

  CertificateStatus(int key) {
    this.key = key;
  }

  public int getKey() {
    return key;
  }
}
