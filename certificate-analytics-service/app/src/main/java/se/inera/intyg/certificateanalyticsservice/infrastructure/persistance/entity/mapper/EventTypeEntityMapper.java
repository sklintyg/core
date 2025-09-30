package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;

public class EventTypeEntityMapper {

  public static EventTypeEntity map(String eventType) {
    return EventTypeEntity.builder()
        .eventType(eventType)
        .build();
  }
}
