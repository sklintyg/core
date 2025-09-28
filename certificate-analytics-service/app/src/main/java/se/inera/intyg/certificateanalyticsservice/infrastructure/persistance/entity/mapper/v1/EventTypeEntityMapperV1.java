package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.common.EventType;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;

public class EventTypeEntityMapperV1 {

  public static EventTypeEntity map(String eventType) {
    final var type = EventType.valueOf(eventType);

    return EventTypeEntity.builder()
        .eventTypeKey(type.getKey())
        .eventType(type.name())
        .build();
  }
}
