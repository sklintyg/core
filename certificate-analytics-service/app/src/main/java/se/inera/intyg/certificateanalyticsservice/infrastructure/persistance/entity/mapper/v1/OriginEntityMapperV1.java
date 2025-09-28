package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.OriginEntity;

public class OriginEntityMapperV1 {

  public static OriginEntity map(String origin) {
    return OriginEntity.builder()
        .origin(origin)
        .build();
  }
}
