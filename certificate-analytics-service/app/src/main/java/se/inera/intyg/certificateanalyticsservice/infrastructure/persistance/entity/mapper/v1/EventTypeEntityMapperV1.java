package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;

public class EventTypeEntityMapperV1 {

  public static EventTypeEntity map(String eventType) {
    return EventTypeEntity.builder()
        .eventType(eventType)
        .build();
  }
}
