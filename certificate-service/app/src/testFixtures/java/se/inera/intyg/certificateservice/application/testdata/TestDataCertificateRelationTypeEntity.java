package se.inera.intyg.certificateservice.application.testdata;

import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateRelationTypeEntity;

public class TestDataCertificateRelationTypeEntity {

  private TestDataCertificateRelationTypeEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final CertificateRelationTypeEntity CERTIFICATE_RELATION_TYPE_ENTITY = certificateRelationEntityTypeBuilder().build();

  public static CertificateRelationTypeEntity.CertificateRelationTypeEntityBuilder certificateRelationEntityTypeBuilder() {
    return CertificateRelationTypeEntity.builder()
        .type(RelationType.REPLACE.name());
  }
}
