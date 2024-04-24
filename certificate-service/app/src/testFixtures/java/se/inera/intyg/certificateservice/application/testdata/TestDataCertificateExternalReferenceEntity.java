package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REF;

import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.ExternalReferenceEntity;

public class TestDataCertificateExternalReferenceEntity {

  private TestDataCertificateExternalReferenceEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final ExternalReferenceEntity EXTERNAL_REFERENCE = externalReferenceBuilder().build();

  public static ExternalReferenceEntity.ExternalReferenceEntityBuilder externalReferenceBuilder() {
    return ExternalReferenceEntity.builder()
        .reference(EXTERNAL_REF);
  }
}
