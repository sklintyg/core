package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.DeviceEntity;

class DeviceEntityMapperV1Test {

  @Test
  void shouldMapDeviceCorrectly() {
    final var expected = DeviceEntity.builder().build();
    final var result = DeviceEntityMapperV1.map();
    assertEquals(expected, result);
  }
}
