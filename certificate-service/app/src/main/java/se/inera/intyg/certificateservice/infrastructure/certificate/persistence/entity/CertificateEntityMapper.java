package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;

public class CertificateEntityMapper {

  public static CertificateEntity toEntity(Certificate certificate) {
    return CertificateEntity.builder()
        .certificateId("b7c07158-bb0f-4785-93fe-228b626d981a")
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
