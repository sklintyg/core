package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.repository.CertificateAnalyticsMessageRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.EventMapperV1;

@RequiredArgsConstructor
public class JpaCertificateAnalyticsEventV1Repository implements
    CertificateAnalyticsMessageRepository {

  private final EventEntityRepository eventEntityRepository;
  private final EventMapperV1 eventMapperV1;

  public void save(CertificateAnalyticsMessage message) {
    if (message instanceof CertificateAnalyticsEventMessageV1 v1Message) {
      final var entity = eventMapperV1.toEntity(v1Message);
      eventEntityRepository.save(entity);
    } else {
      throw new IllegalArgumentException(
          "Only CertificateAnalyticsEventMessageV1 is supported by this repository");
    }
  }
}
