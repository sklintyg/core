package se.inera.intyg.certificateanalyticsservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateanalyticsservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages.toJson;

import java.time.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.infrastructure.logging.MdcLogConstants;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.Containers;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages;

@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CertificateAnalyticsEventMessageV1IT {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private JmsTemplate jmsTemplate;

  @Value("${certificate.analytics.message.queue.name}")
  private String queueName;

  @LocalServerPort
  private int port;

  @BeforeAll
  static void beforeAll() {
    Containers.ensureRunning();
  }

  @Test
  void shallProcessAndPersistMessage() {
    final var expected = TestDataMessages.draftMessageBuilder().build();

    publishMessage(expected);

    final var actual = awaitProcessed(expected.getMessageId(), Duration.ofSeconds(5));

    assertEquals(expected, actual);
  }

  private void publishMessage(CertificateAnalyticsEventMessageV1 expected) {
    jmsTemplate.convertAndSend(queueName, toJson(expected), msg -> {
          msg.setStringProperty("messageId", expected.getMessageId());
          msg.setStringProperty("sessionId", expected.getEvent().getSessionId());
          msg.setStringProperty("traceId", MDC.get(MdcLogConstants.TRACE_ID_KEY));
          msg.setStringProperty("_type", expected.getType());
          msg.setStringProperty("schemaVersion", expected.getSchemaVersion());
          msg.setStringProperty("contentType", "application/json");
          msg.setStringProperty("messageType", expected.getEvent().getMessageType().toString());
          return msg;
        }
    );
  }

  private CertificateAnalyticsEventMessageV1 awaitProcessed(String messageId, Duration timeout) {
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
}