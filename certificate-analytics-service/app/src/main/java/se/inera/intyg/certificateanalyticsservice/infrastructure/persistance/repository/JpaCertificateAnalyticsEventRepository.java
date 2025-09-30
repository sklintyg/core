package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.repository.CertificateAnalyticsMessageRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.EventMapper;

@Repository
@RequiredArgsConstructor
public class JpaCertificateAnalyticsEventRepository implements
    CertificateAnalyticsMessageRepository {

  private final EventEntityRepository eventEntityRepository;
  private final EventMapper eventMapper;

  public void save(PseudonymizedAnalyticsMessage message) {
    final var entity = eventMapper.toEntity(message);
    eventEntityRepository.save(entity);
  }

  @Override
  public PseudonymizedAnalyticsMessage findByMessageId(String messageId) {
    return eventEntityRepository.findByMessageId(messageId)
        .map(eventMapper::toDomain)
        .orElse(null);
  }
}
