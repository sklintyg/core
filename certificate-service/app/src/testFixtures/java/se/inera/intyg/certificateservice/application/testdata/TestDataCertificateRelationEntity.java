package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateEntity.CERTIFICATE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateEntity.PARENT_CERTIFICATE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateRelationTypeEntity.CERTIFICATE_RELATION_TYPE_ENTITY;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateRelationEntity;

public class TestDataCertificateRelationEntity {

  private TestDataCertificateRelationEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final CertificateRelationEntity CERTIFICATE_RELATION_ENTITY = certificateRelationEntityBuilder().build();
  public static final CertificateRelationEntity CERTIFICATE_PARENT_RELATION_ENTITY = certificateParentRelationEntityBuilder().build();

  public static CertificateRelationEntity.CertificateRelationEntityBuilder certificateRelationEntityBuilder() {
    return CertificateRelationEntity.builder()
        .parentCertificate(PARENT_CERTIFICATE_ENTITY)
        .childCertificate(CERTIFICATE_ENTITY)
        .created(LocalDateTime.now())
        .certificateRelationType(CERTIFICATE_RELATION_TYPE_ENTITY);
  }

  public static CertificateRelationEntity.CertificateRelationEntityBuilder certificateParentRelationEntityBuilder() {
    return CertificateRelationEntity.builder()
        .parentCertificate(CERTIFICATE_ENTITY)
        .childCertificate(PARENT_CERTIFICATE_ENTITY)
        .created(LocalDateTime.now())
        .certificateRelationType(CERTIFICATE_RELATION_TYPE_ENTITY);
  }
}
