package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.repository.CertificateAnalyticsMessageRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.EventMapper;

@RequiredArgsConstructor
public class JpaCertificateAnalyticsEventRepository implements
    CertificateAnalyticsMessageRepository {

  private final EventEntityRepository eventEntityRepository;
  private final List<EventMapper> eventMappers;

  public void save(CertificateAnalyticsMessage message) {
    final var mapper = eventMappers.stream()
        .filter(m -> m.supports(message))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            "No EventMapper found for message type: " + message.getClass().getSimpleName()));
    final var entity = mapper.toEntity(message);
    eventEntityRepository.save(entity);
  }
}
