package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason.INCORRECT_PATIENT;

import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReasonEntity;

public class TestDataCertificateRevokedEntity {

  private TestDataCertificateRevokedEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final String REVOKED_MESSAGE = "REVOKED_MESSAGE";
  public static final RevokedReasonEntity REVOKED_REASON_ENTITY = RevokedReasonEntity.builder()
      .key(INCORRECT_PATIENT.getKey())
      .reason(INCORRECT_PATIENT.name())
      .build();

}
