package se.inera.intyg.cts.infrastructure.persistence.entity;

import java.util.Base64;
import se.inera.intyg.cts.domain.model.CertificateText;
import se.inera.intyg.cts.domain.model.CertificateType;
import se.inera.intyg.cts.domain.model.CertificateTypeVersion;
import se.inera.intyg.cts.domain.model.CertificateXML;

public class CertificateTextEntityMapper {

  public static CertificateTextEntity toEntity(CertificateText certificate,
      TerminationEntity terminationEntity) {
    return new CertificateTextEntity(
        0L,
        certificate.certificateType().type(),
        certificate.certificateTypeVersion().version(),
        Base64.getEncoder().encodeToString(certificate.certificateXML().xml().getBytes()),
        terminationEntity);
  }

  public static CertificateText toDomain(CertificateTextEntity certificateTextEntity) {
    return new CertificateText(
        new CertificateType(certificateTextEntity.getCertificateType()),
        new CertificateTypeVersion(certificateTextEntity.getCertificateTypeVersion()),
        new CertificateXML(new String(Base64.getDecoder().decode(certificateTextEntity.getXml())))
    );
  }
}
