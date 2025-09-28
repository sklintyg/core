package se.inera.intyg.certificateanalyticsservice.application.messages.repository;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;

public interface CertificateAnalyticsMessageRepository {

  EventEntity save(CertificateAnalyticsMessage message);
}

