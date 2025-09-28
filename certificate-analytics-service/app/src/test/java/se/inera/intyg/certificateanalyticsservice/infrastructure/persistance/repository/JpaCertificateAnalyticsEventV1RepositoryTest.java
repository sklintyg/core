package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.EventMapperV1;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataCertificateAnalyticsMessages;

@ExtendWith(MockitoExtension.class)
class JpaCertificateAnalyticsEventV1RepositoryTest {

  @Mock
  private EventEntityRepository eventEntityRepository;

  @InjectMocks
  private JpaCertificateAnalyticsEventV1Repository jpaCertificateAnalyticsEventV1Repository;

  @Test
  void shouldMapAndSaveCreatedEventMessage() {
    final var message = TestDataCertificateAnalyticsMessages.createdEventMessage();
    final var entityToSave = EventMapperV1.toEntity(
        TestDataCertificateAnalyticsMessages.createdEventMessage());
    final var savedEntity = mock(EventEntity.class);
    when(eventEntityRepository.save(entityToSave)).thenReturn(savedEntity);

    final var result = jpaCertificateAnalyticsEventV1Repository.save(message);

    assertEquals(savedEntity, result);
  }
}

