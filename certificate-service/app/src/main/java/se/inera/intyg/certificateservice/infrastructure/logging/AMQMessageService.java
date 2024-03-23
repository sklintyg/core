package se.inera.intyg.certificateservice.infrastructure.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventSubscriber;

@Component
@Slf4j
@RequiredArgsConstructor
public class AMQMessageService implements CertificateEventSubscriber {

  private final JmsTemplate certificateEventJmsTemplate;

  private static final String CERTIFICATE_SIGNED = "certificate-signed";

  @Override
  public void event(CertificateEvent event) {
    if (event.type() == CertificateEventType.SIGNED) {
      sendJms(event.certificate().id().id(), CERTIFICATE_SIGNED);
    }
  }

  public void sendJms(String certificateId, String eventType) {
    try {
      certificateEventJmsTemplate.send(session -> {
        final var textMessage = session.createTextMessage();
        textMessage.setStringProperty("certificateId", certificateId);
        textMessage.setStringProperty("eventType", eventType);
        return textMessage;
      });
    } catch (Exception e) {
      log.error("Failure sending event notification of type '{}' for certificate '{}'.",
          eventType, certificateId, e);
    }
  }
}
