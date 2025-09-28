package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataCertificateAnalyticsMessages;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

class EventMapperV1Test {

  @Test
  void shouldMapCreatedEventMessageCorrectly() {
    final var message = TestDataCertificateAnalyticsMessages.createdEventMessage();
    final var expected = EventEntity.builder()
        .certificate(TestDataEntities.certificateEntity())
        .unit(TestDataEntities.createdEventEntity().getUnit())
        .careProvider(TestDataEntities.createdEventEntity().getCareProvider())
        .user(TestDataEntities.createdEventEntity().getUser())
        .session(TestDataEntities.createdEventEntity().getSession())
        .time(TestDataEntities.createdEventEntity().getTime())
        .origin(TestDataEntities.createdEventEntity().getOrigin())
        .device(TestDataEntities.createdEventEntity().getDevice())
        .eventType(TestDataEntities.createdEventEntity().getEventType())
        .role(TestDataEntities.createdEventEntity().getRole())
        .build();

    final var result = EventMapperV1.toEntity(message);

    assertEquals(expected, result);
  }
}
