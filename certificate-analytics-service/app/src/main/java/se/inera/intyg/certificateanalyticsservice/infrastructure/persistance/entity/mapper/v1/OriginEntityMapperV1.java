package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.common.OriginType;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.OriginEntity;

public class OriginEntityMapperV1 {

  public static OriginEntity map(String origin) {
    final var type = OriginType.valueOf(origin);
    return OriginEntity.builder()
        .originKey(type.getKey())
        .origin(origin)
        .build();
  }
}
