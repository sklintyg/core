package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;

public class CertificateTypeEntityMapperV1 {

  public static CertificateTypeEntity map(String type, String version) {
    return CertificateTypeEntity.builder()
        .certificateType(type)
        .certificateTypeVersion(version)
        .build();
  }
}
