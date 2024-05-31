package se.inera.intyg.certificateservice.infrastructure.messaging;

import jakarta.jms.JMSException;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.MessageEvent;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventSubscriber;
import se.inera.intyg.certificateservice.domain.event.service.MessageEventSubscriber;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventMessageService implements CertificateEventSubscriber, MessageEventSubscriber {

  private final JmsTemplate jmsTemplate;

  @Override
  public void event(CertificateEvent event) {
    if (event.type().hasMessageType()) {
      jmsTemplate.send(session -> getCertificateEventMessage(
          event.certificate().id().id(),
          event.type().messageType(),
          session)
      );
    }
  }

  @Override
  public void event(MessageEvent event) {
    jmsTemplate.send(
        session -> getMessageEventMessage(
            event.answer().id().id(),
            event.type().messageType(),
            session)
    );
  }

  private static TextMessage getMessageEventMessage(String messageId, String eventType,
      Session session)
      throws JMSException {
    final var message = session.createTextMessage();
    message.setStringProperty("messageId", messageId);
    message.setStringProperty("eventType", eventType);
    return message;
  }

  private static TextMessage getCertificateEventMessage(String certificateId, String eventType,
      Session session)
      throws JMSException {
    final var message = session.createTextMessage();
    message.setStringProperty("certificateId", certificateId);
    message.setStringProperty("eventType", eventType);
    return message;
  }
}
