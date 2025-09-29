package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;

class EventTypeEntityMapperV1Test {

  @Test
  void shouldMapEventTypeCorrectly() {
    final var expected = EventTypeEntity.builder()
        .eventType("CREATED")
        .build();

    final var result = EventTypeEntityMapperV1.map("CREATED");

    assertEquals(expected, result);
  }
}

