package se.inera.intyg.certificateanalyticsservice.integrationtest.util;

import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages.toJson;

import jakarta.jms.Message;
import java.time.Duration;
import java.util.function.Predicate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.infrastructure.logging.MdcHelper;

public class JmsUtil {

  public static final String DLQ_QUEUE_NAME = "ActiveMQ.DLQ";

  private final JmsTemplate jmsTemplate;
  private final String queueName;

  public JmsUtil(JmsTemplate jmsTemplate, String queueName) {
    this.jmsTemplate = jmsTemplate;
    this.queueName = queueName;
  }

  public void reset() {
    purgeQueue(queueName);
    purgeQueue(DLQ_QUEUE_NAME);
  }

  public void publishMessage(CertificateAnalyticsEventMessageV1 message) {
    jmsTemplate.convertAndSend(queueName, toJson(message), messagePostProcessor(message));
  }

  public void publishUnparsableMessage(CertificateAnalyticsEventMessageV1 message) {
    jmsTemplate.convertAndSend(queueName, "unparsableMessage", messagePostProcessor(message));
  }

  public boolean awaitProcessedToDlq(String messageId, Duration timeout) {
    final var deadline = System.nanoTime() + timeout.toNanos();
    while (System.nanoTime() < deadline) {
      final var resp = dlqContains(messageId);

      if (resp) {
        return true;
      }

      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return false;
  }

  private void purgeQueue(String queueName) {
    jmsTemplate.setReceiveTimeout(100);
    while (true) {
      final var msg = jmsTemplate.receive(queueName);
      if (msg == null) {
        break;
      }
    }
  }

  private static MessagePostProcessor messagePostProcessor(
      CertificateAnalyticsEventMessageV1 message) {
    return msg -> {
      msg.setStringProperty("messageId", message.getMessageId());
      msg.setStringProperty("sessionId", message.getEvent().getSessionId());
      msg.setStringProperty("traceId", MdcHelper.traceId());
      msg.setStringProperty("_type", message.getType());
      msg.setStringProperty("schemaVersion", message.getSchemaVersion());
      msg.setStringProperty("contentType", "application/json");
      msg.setStringProperty("messageType", message.getEvent().getMessageType());
      return msg;
    };
  }

  private boolean dlqContains(String messageId) {
    final var pred = matchingMessageIdPredicate(messageId);
    return Boolean.TRUE.equals(
        jmsTemplate.browse(DLQ_QUEUE_NAME, (session, browser) -> {
              var e = browser.getEnumeration();
              while (e.hasMoreElements()) {
                final var m = (Message) e.nextElement();
                if (pred.test(m)) {
                  return true;
                }
              }
              return false;
            }
        )
    );
  }

  private Predicate<Message> matchingMessageIdPredicate(String messageId) {
    return m -> {
      try {
        return messageId.equals(m.getStringProperty("messageId"));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }
}