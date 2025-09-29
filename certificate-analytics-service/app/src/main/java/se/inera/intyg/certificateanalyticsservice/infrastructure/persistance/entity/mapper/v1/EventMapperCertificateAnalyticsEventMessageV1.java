package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;

@RequiredArgsConstructor
public class EventMapperCertificateAnalyticsEventMessageV1 implements EventMapper {

  private final EventMapperV1 eventMapperV1;

  @Override
  public EventEntity toEntity(CertificateAnalyticsMessage message) {
    return eventMapperV1.toEntity((CertificateAnalyticsEventMessageV1) message);
  }

  @Override
  public boolean supports(CertificateAnalyticsMessage message) {
    return message instanceof CertificateAnalyticsEventMessageV1;
  }
}
