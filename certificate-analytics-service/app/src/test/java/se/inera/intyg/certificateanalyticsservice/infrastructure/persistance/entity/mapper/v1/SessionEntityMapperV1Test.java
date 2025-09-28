package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.SessionEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class SessionEntityMapperV1Test {

  @Test
  void shouldMapSessionIdCorrectly() {
    final var expected = SessionEntity.builder()
        .sessionId(TestDataConstants.SESSION_ID)
        .build();
    final var result = SessionEntityMapperV1.map(TestDataConstants.SESSION_ID);
    assertEquals(expected, result);
  }
}

