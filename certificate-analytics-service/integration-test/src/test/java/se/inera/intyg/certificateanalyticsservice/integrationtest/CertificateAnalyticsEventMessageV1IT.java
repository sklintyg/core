package se.inera.intyg.certificateanalyticsservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateanalyticsservice.testability.configuration.TestabilityConfiguration.TESTABILITY_PROFILE;

import java.time.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.Containers;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.JmsUtil;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.TestabilityUtil;
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

  private JmsUtil jmsUtil;
  private TestabilityUtil testabilityUtil;

  @BeforeAll
  static void beforeAll() {
    Containers.ensureRunning();
  }

  @BeforeEach
  void setUp() {
    jmsUtil = new JmsUtil(jmsTemplate, queueName);
    testabilityUtil = new TestabilityUtil(restTemplate, port);

    jmsUtil.reset();
    testabilityUtil.reset();
  }

  @Test
  void shallProcessAndPersistMessage() {
    final var expected = TestDataMessages.draftMessageBuilder().build();

    jmsUtil.publishMessage(expected);

    final var actual = testabilityUtil.awaitProcessed(
        expected.getMessageId(),
        Duration.ofSeconds(5)
    );

    assertEquals(expected, actual);
  }

  @Test
  void shallProcessAndPersistMessageThroughRedelivery() {
    final var expected = TestDataMessages.draftMessageBuilder().build();

    testabilityUtil.toggleTemporaryFailure(2);

    jmsUtil.publishMessage(expected);

    final var actual = testabilityUtil.awaitProcessed(
        expected.getMessageId(),
        Duration.ofSeconds(5)
    );

    assertEquals(expected, actual);
  }

  @Test
  void shallMoveToDlqAfterMaximumRedeliveries() {
    final var message = TestDataMessages.draftMessageBuilder().build();

    testabilityUtil.togglePermanentFailure(true);

    jmsUtil.publishMessage(message);

    final var actual = jmsUtil.awaitProcessedToDlq(message.getMessageId(), Duration.ofSeconds(5));

    assertTrue(actual, "DLQ should contain the message");
  }

  @Test
  void shallMoveToDlqIfMessageCannotBeParsed() {
    final var message = TestDataMessages.draftMessageBuilder().build();

    jmsUtil.publishUnparsableMessage(message);

    final var actual = jmsUtil.awaitProcessedToDlq(message.getMessageId(), Duration.ofSeconds(5));

    assertTrue(actual, "DLQ should contain the message");
  }
}