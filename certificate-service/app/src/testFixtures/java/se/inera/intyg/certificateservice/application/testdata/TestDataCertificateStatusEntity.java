package se.inera.intyg.certificateservice.application.testdata;

import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatus;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatusEntity;

public class TestDataCertificateStatusEntity {

  private TestDataCertificateStatusEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final CertificateStatusEntity STATUS_SIGNED_ENTITY = statusSignedEntityBuilder().build();
  public static final CertificateStatusEntity STATUS_DRAFT_ENTITY = statusDraftEntityBuilder().build();

  public static CertificateStatusEntity.CertificateStatusEntityBuilder statusSignedEntityBuilder() {
    return CertificateStatusEntity.builder()
        .key(CertificateStatus.SIGNED.getKey())
        .status(CertificateStatus.SIGNED.name());
  }

  public static CertificateStatusEntity.CertificateStatusEntityBuilder statusDraftEntityBuilder() {
    return CertificateStatusEntity.builder()
        .key(CertificateStatus.DRAFT.getKey())
        .status(CertificateStatus.DRAFT.name());
  }

}
