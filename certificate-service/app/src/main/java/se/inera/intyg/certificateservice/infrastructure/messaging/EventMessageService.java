package se.inera.intyg.certificateservice.infrastructure.messaging;

import jakarta.jms.JMSException;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventSubscriber;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventMessageService implements CertificateEventSubscriber {

  private final JmsTemplate jmsTemplate;

  @Override
  public void event(CertificateEvent event) {
    if (event.type().hasMessageType()) {
      sendMessage(
          event.certificate().id().id(),
          event.type().messageType()
      );
    }
  }

  private void sendMessage(String certificateId, String eventType) {
    jmsTemplate.send(session -> getMessage(certificateId, eventType, session));
  }

  private static TextMessage getMessage(String certificateId, String eventType, Session session)
      throws JMSException {
    final var message = session.createTextMessage();
    message.setStringProperty("certificateId", certificateId);
    message.setStringProperty("eventType", eventType);
    return message;
  }
}
