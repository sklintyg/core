package se.inera.intyg.certificateanalyticsservice.integrationtest;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.jms.TextMessage;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

public class GetMessageIT extends BaseIntegrationIT {

  @Autowired
  private JmsTemplate jmsTemplate;

  @Value("${certificate.event.queue.name}")
  private String queueName;

  private static final int maxWaitTimeSeconds = 1;

  @Test
  void shouldConsumeMessage() {
    final var testMessage = String.format("Test Message");
    jmsTemplate.convertAndSend(queueName, testMessage);

    await()
        .atMost(Duration.ofSeconds(maxWaitTimeSeconds))
        .until(() -> !testListener.messages().isEmpty());

    assertFalse(testListener.messages().isEmpty());

    final TextMessage receivedMessage = testListener.messages().get(0);

    assertAll(() -> {
      assertNotNull(receivedMessage);
      assertEquals(testMessage, receivedMessage.getText());
    });
  }
}
