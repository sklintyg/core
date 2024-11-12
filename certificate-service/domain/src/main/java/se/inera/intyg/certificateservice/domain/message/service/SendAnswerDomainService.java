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
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@RequiredArgsConstructor
public class SendAnswerDomainService {

  private final MessageRepository messageRepository;
  private final MessageEventDomainService messageEventDomainService;

  public Message send(Message message, Certificate certificate,
      ActionEvaluation actionEvaluation, Content content) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());
    if (!certificate.allowTo(CertificateActionType.SEND_ANSWER,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to send answer on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.SEND_ANSWER, Optional.empty())
      );
    }

    message.sendAnswer(
        Staff.create(actionEvaluation.user()), content
    );

    final var messageWithSentAnswer = messageRepository.save(message);

    messageEventDomainService.publish(
        MessageEvent.builder()
            .type(MessageEventType.SEND_ANSWER)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .messageId(messageWithSentAnswer.answer().id())
            .certificateId(certificate.id())
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return messageWithSentAnswer;
  }
}