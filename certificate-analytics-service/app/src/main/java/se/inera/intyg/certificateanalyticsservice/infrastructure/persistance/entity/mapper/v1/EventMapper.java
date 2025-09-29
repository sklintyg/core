package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;

public interface EventMapper {

  EventEntity toEntity(CertificateAnalyticsMessage message);

  boolean supports(CertificateAnalyticsMessage message);
}

