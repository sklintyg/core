package se.inera.intyg.certificateanalyticsservice.application.messages.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;

/**
 * This is just a stub repository for storing messages in memory. It will be replaced later with a
 * real implementation that stores messages in a database.
 */
@Repository
public class AnalyticMessageRepository {

  private final List<CertificateAnalyticsMessage> messages = new ArrayList<>();

  public void store(CertificateAnalyticsMessage message) {
    messages.add(message);
  }
}
