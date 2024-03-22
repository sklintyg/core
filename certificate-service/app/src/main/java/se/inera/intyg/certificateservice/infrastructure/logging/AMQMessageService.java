package se.inera.intyg.certificateservice.infrastructure.logging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventSubscriber;

@Component
@RequiredArgsConstructor
public class AMQMessageService implements CertificateEventSubscriber {

  private final RabbitTemplate rabbitTemplate;

  @Value("${certificate.service.amq.exchange}")
  private String queueExchange;
  @Value("${certificate.service.amq.routing.key}")
  private String routingKey;

  @Override
  public void event(CertificateEvent event) {
    if (event.type() == CertificateEventType.SIGNED) {
      rabbitTemplate.convertAndSend(queueExchange, routingKey, "Test message");
    }
  }
}
