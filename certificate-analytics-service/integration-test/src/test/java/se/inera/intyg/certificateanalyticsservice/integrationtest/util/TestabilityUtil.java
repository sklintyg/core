package se.inera.intyg.certificateanalyticsservice.integrationtest.util;

import java.time.Duration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;

public class TestabilityUtil {

  private final int port;
  private final TestRestTemplate restTemplate;

  public TestabilityUtil(TestRestTemplate restTemplate, int port) {
    this.restTemplate = restTemplate;
    this.port = port;
  }

  public CertificateAnalyticsEventMessageV1 awaitProcessed(String messageId, Duration timeout) {
    final var deadline = System.nanoTime() + timeout.toNanos();
    while (System.nanoTime() < deadline) {
      final var resp = restTemplate.getForEntity(
          "http://localhost:%s/testability/messages/v1/%s".formatted(port, messageId),
          CertificateAnalyticsEventMessageV1.class
      );

      if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
        return resp.getBody();
      }

      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    throw new AssertionError("Message " + messageId + " was not processed within " + timeout);
  }

  public void reset() {
    restTemplate.getForEntity(
        "http://localhost:%s/testability/messages/reset".formatted(port),
        Void.class
    );
  }

  public void toggleTemporaryFailure(int numberOfFailures) {
    restTemplate.getForEntity(
        "http://localhost:%s/testability/messages/fail/temporary/%s"
            .formatted(port, numberOfFailures),
        Void.class
    );
  }

  public void togglePermanentFailure(boolean permanentFailure) {
    restTemplate.getForEntity(
        "http://localhost:%s/testability/messages/fail/permanent/%s"
            .formatted(port, permanentFailure),
        Void.class
    );
  }
}