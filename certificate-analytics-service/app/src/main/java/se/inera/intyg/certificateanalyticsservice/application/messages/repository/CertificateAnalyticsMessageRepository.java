package se.inera.intyg.certificateanalyticsservice.application.messages.repository;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;

public interface CertificateAnalyticsMessageRepository {

  void save(CertificateAnalyticsMessage message);
}
