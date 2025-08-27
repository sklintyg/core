package se.inera.intyg.certificateservice.infrastructure.messaging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.model.MessageEvent;
import se.inera.intyg.certificateservice.domain.event.model.MessageEventType;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;

@ExtendWith(MockitoExtension.class)
class EventMessageServiceTest {

  private static final MessageId MESSAGE_ID = new MessageId("ID");
  @InjectMocks
  EventMessageService eventMessageService;

  @Mock
  JmsTemplate jmsTemplate;

  @Nested
  class CertificateEventTests {

    @Test
    void shouldLogForSignedEvent() {
      final var event = createEvent(CertificateEventType.SIGNED);

      eventMessageService.event(event);

      verify(jmsTemplate, times(1)).send(any());
    }

    @Test
    void shouldLogForSentEvent() {
      final var event = createEvent(CertificateEventType.SENT);

      eventMessageService.event(event);

      verify(jmsTemplate, times(1)).send(any());
    }

    @Test
    void shouldNotLogForReadEvent() {
      final var event = createEvent(CertificateEventType.READ);

      eventMessageService.event(event);

      verify(jmsTemplate, times(0)).send(any());
    }

    private CertificateEvent createEvent(CertificateEventType eventType) {
      return CertificateEvent.builder()
          .certificate(
              MedicalCertificate.builder()
                  .id(new CertificateId("ID"))
                  .build()
          )
          .type(
              eventType
          )
          .build();
    }
  }

  @Nested
  class MessageEventTests {

    @Test
    void shouldSendEvent() {
      final var event = createEvent();

      eventMessageService.event(event);

      verify(jmsTemplate, times(1)).send(any());
    }

    private MessageEvent createEvent() {
      return MessageEvent.builder()
          .messageId(MESSAGE_ID)
          .type(MessageEventType.ANSWER_COMPLEMENT)
          .build();
    }
  }
}