package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.OriginEntity;

public class OriginEntityMapper {

  public static OriginEntity map(String origin) {
    return OriginEntity.builder()
        .origin(origin)
        .build();
  }
}
