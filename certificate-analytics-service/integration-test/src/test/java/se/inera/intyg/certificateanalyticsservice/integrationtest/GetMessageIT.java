package se.inera.intyg.certificateanalyticsservice.integrationtest;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class GetMessageIT extends BaseIntegrationIT {

  @Autowired
  private JmsTemplate jmsTemplate;


  @Test
  void shouldConsumeMessage() throws JMSException {
    // Given
    final var testMessage = String.format("Test message for type: %s, version: %s", "DUMMY",
        "DUMMY-1.0");
    final var queueName = "${certificate.event.queue.name}";

    // When - Send a message to the AMQ queue
    jmsTemplate.convertAndSend(queueName, testMessage);

    // Then - Wait for message to be consumed by TestListener and verify
    await()
        .atMost(Duration.ofSeconds(1))
        .until(() -> !testListener.messages().isEmpty());

    assertFalse(testListener.messages().isEmpty(), "Should have received at least one message");

    final TextMessage receivedMessage = testListener.messages().get(0);
    assertNotNull(receivedMessage, "Received message should not be null");
    assertEquals(testMessage, receivedMessage.getText(),
        "Message content should match what was sent");
  }
}
