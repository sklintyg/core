package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.common.EventType;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;

class EventTypeEntityMapperV1Test {

  @Test
  void shouldMapEventTypeCorrectly() {
    final var expected = EventTypeEntity.builder()
        .eventType(EventType.CREATED.name())
        .build();
    final var result = EventTypeEntityMapperV1.map(EventType.CREATED.name());
    assertEquals(expected, result);
  }
}

