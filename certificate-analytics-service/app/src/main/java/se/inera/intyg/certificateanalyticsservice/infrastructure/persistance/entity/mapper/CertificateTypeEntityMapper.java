package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;

public class CertificateTypeEntityMapper {

  private CertificateTypeEntityMapper() {
    throw new IllegalStateException("Utility class");
  }
  
  public static CertificateTypeEntity map(String type, String version) {
    return CertificateTypeEntity.builder()
        .certificateType(type)
        .certificateTypeVersion(version)
        .build();
  }
}
