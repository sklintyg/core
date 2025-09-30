package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.repository.CertificateAnalyticsMessageRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.EventMapper;

@RequiredArgsConstructor
public class JpaCertificateAnalyticsEventRepository implements
    CertificateAnalyticsMessageRepository {

  private final EventEntityRepository eventEntityRepository;
  private final EventMapper eventMapper;

  public void save(PseudonymizedAnalyticsMessage message) {
    final var entity = eventMapper.toEntity(message);
    eventEntityRepository.save(entity);
  }
}
