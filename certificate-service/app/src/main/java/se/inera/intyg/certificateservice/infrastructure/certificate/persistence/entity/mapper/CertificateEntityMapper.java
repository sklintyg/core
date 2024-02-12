package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;

public class CertificateEntityMapper {

  private CertificateEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateEntity toEntity(Certificate certificate) {
    return CertificateEntity.builder()
        .certificateId(certificate.id().id())
        .created(certificate.created())
        .version(certificate.version() + 1)
        .modified(LocalDateTime.now())
        .build();
  }

  public static CertificateEntity updateEntity(CertificateEntity entity) {
    entity.setVersion(entity.getVersion() + 1);
    entity.setModified(LocalDateTime.now());
    return entity;
  }

  public static Certificate toDomain(CertificateEntity certificateEntity) {
    return Certificate.builder()
        .id(new CertificateId(certificateEntity.getCertificateId()))
        .created(certificateEntity.getCreated())
        .build();
  }

}
