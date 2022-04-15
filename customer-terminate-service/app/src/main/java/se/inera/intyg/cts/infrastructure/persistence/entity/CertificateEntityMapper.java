package se.inera.intyg.cts.infrastructure.persistence.entity;

import se.inera.intyg.cts.domain.model.Certificate;
import se.inera.intyg.cts.domain.model.CertificateId;
import se.inera.intyg.cts.domain.model.CertificateXML;

public class CertificateEntityMapper {

  public static CertificateEntity toEntity(Certificate certificate,
      TerminationEntity terminationEntity) {
    return new CertificateEntity(
        certificate.certificateId().id(),
        certificate.certificateXML().xml(),
        certificate.revoked(),
        terminationEntity);
  }

  public static Certificate toDomain(CertificateEntity certificateEntity) {
    return new Certificate(
        new CertificateId(certificateEntity.getCertificateId()),
        certificateEntity.isRevoked(),
        new CertificateXML(certificateEntity.getXml())
    );
  }
}
