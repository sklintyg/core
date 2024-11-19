package se.inera.intyg.certificateservice.domain.message.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.MessageEvent;
import se.inera.intyg.certificateservice.domain.event.model.MessageEventType;
import se.inera.intyg.certificateservice.domain.event.service.MessageEventDomainService;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class SendMessageDomainService {

  private final MessageRepository messageRepository;
  private final MessageEventDomainService messageEventDomainService;

  public Message send(Message message, Certificate certificate,
      ActionEvaluation actionEvaluation) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());
    if (!certificate.allowTo(CertificateActionType.SEND_MESSAGE,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to send messages on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.SEND_MESSAGE, Optional.empty())
      );
    }

    message.send();

    final var sentMessage = messageRepository.save(message);

    messageEventDomainService.publish(
        MessageEvent.builder()
            .type(MessageEventType.SEND_QUESTION)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .messageId(message.id())
            .certificateId(certificate.id())
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return sentMessage;
  }
}
