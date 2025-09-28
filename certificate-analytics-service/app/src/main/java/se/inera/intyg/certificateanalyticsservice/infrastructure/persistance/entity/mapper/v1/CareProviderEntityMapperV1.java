package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CareProviderEntity;

public class CareProviderEntityMapperV1 {

  public static CareProviderEntity map(String careProviderId) {
    return CareProviderEntity.builder()
        .hsaId(careProviderId)
        .build();
  }
}