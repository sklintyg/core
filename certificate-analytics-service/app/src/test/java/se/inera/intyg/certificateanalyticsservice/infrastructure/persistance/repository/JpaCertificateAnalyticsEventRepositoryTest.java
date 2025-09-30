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
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.EventMapper;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataPseudonymized;

@ExtendWith(MockitoExtension.class)
class JpaCertificateAnalyticsEventRepositoryTest {

  @Mock
  private EventEntityRepository eventEntityRepository;
  @Mock
  private EventMapper eventMapper;
  @InjectMocks
  private JpaCertificateAnalyticsEventRepository jpaCertificateAnalyticsEventRepository;

  @Test
  void shouldMapAndSaveCreatedEventMessage() {
    final var message = TestDataPseudonymized.draftPseudonymizedMessageBuilder().build();
    final var entityToSave = mock(EventEntity.class);
    when(eventMapper.toEntity(message)).thenReturn(entityToSave);
    when(eventEntityRepository.save(entityToSave)).thenReturn(entityToSave);

    jpaCertificateAnalyticsEventRepository.save(message);

    verify(eventEntityRepository).save(entityToSave);
  }
}
