package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.EventMapper;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages;

@ExtendWith(MockitoExtension.class)
class JpaCertificateAnalyticsEventRepositoryTest {

  @Mock
  private EventEntityRepository eventEntityRepository;
  @Mock
  private EventMapper eventMapper;

  @Test
  void shouldMapAndSaveCreatedEventMessage() {
    final var message = TestDataMessages.CREATED_EVENT_MESSAGE;
    final var entityToSave = mock(EventEntity.class);
    when(eventMapper.supports(message)).thenReturn(true);
    when(eventMapper.toEntity(message)).thenReturn(entityToSave);
    JpaCertificateAnalyticsEventRepository repo = new JpaCertificateAnalyticsEventRepository(
        eventEntityRepository, List.of(eventMapper));
    when(eventEntityRepository.save(entityToSave)).thenReturn(entityToSave);

    repo.save(message);

    verify(eventEntityRepository).save(entityToSave);
  }
}
