package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataCertificateAnalyticsMessages;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

class EventMapperV1Test {

  @Test
  void shouldMapCreatedEventMessageCorrectly() {
    final var expected = TestDataEntities.createdEventEntity();

    final var result = EventMapperV1.toEntity(
        TestDataCertificateAnalyticsMessages.createdEventMessage()
    );

    assertEquals(expected, result);
  }
}
