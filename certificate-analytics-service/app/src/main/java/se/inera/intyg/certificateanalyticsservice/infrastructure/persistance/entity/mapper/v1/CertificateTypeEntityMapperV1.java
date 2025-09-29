package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventCertificateV1;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;

public class CertificateTypeEntityMapperV1 {

  public static CertificateTypeEntity map(CertificateAnalyticsEventCertificateV1 certificate) {
    return CertificateTypeEntity.builder()
        .certificateType(certificate.getType())
        .certificateTypeVersion(certificate.getTypeVersion())
        .build();
  }
}

