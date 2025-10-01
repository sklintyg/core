package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CareProviderEntity;

public class CareProviderEntityMapper {

  public static CareProviderEntity map(String careProviderId) {
    return CareProviderEntity.builder()
        .hsaId(careProviderId)
        .build();
  }
}