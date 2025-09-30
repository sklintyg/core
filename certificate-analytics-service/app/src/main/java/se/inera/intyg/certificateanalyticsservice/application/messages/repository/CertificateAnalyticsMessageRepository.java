package se.inera.intyg.certificateanalyticsservice.application.messages.repository;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;

public interface CertificateAnalyticsMessageRepository {

  void save(PseudonymizedAnalyticsMessage message);
}
