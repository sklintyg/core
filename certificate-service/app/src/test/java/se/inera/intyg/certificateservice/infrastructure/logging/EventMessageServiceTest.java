package se.inera.intyg.certificateservice.infrastructure.logging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;

@ExtendWith(MockitoExtension.class)
class EventMessageServiceTest {

  @InjectMocks
  EventMessageService eventMessageService;

  @Mock
  JmsTemplate jmsTemplate;

  @Test
  void shouldLogForSignedEvent() {
    final var event = createEvent(CertificateEventType.SIGNED);

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
            Certificate.builder()
                .id(new CertificateId("ID"))
                .build()
        )
        .type(
            eventType
        )
        .build();
  }

}