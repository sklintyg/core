package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.OriginEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class OriginEntityMapperV1Test {

  @Test
  void shouldMapOriginCorrectly() {
    final var expected = OriginEntity.builder()
        .origin(TestDataConstants.ORIGIN)
        .build();
    final var result = OriginEntityMapperV1.map(TestDataConstants.ORIGIN);
    assertEquals(expected, result);
  }
}

