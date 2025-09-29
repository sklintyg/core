package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.EventMapperV1;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages;

@ExtendWith(MockitoExtension.class)
class JpaCertificateAnalyticsEventRepositoryTest {

  @Mock
  private EventEntityRepository eventEntityRepository;
  @Mock
  private EventMapperV1 eventMapperV1;

  @InjectMocks
  private JpaCertificateAnalyticsEventRepository jpaCertificateAnalyticsEventRepository;

  @Test
  void shouldMapAndSaveCreatedEventMessage() {
    final var message = TestDataMessages.CREATED_EVENT_MESSAGE;
    final var entityToSave = eventMapperV1.toEntity(TestDataMessages.CREATED_EVENT_MESSAGE);
    when(eventEntityRepository.save(entityToSave)).thenReturn(mock(EventEntity.class));

    jpaCertificateAnalyticsEventRepository.save(message);

    verify(eventEntityRepository).save(entityToSave);
  }
}
