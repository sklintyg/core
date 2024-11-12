package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.MessageEvent;
import se.inera.intyg.certificateservice.domain.event.model.MessageEventType;
import se.inera.intyg.certificateservice.domain.event.service.MessageEventDomainService;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.service.SetMessagesToHandleDomainService;

@RequiredArgsConstructor
public class AnswerComplementDomainService {

  private final CertificateRepository certificateRepository;
  private final SetMessagesToHandleDomainService setMessagesToHandleDomainService;
  private final MessageEventDomainService messageEventDomainService;

  public Certificate answer(CertificateId certificateId, ActionEvaluation actionEvaluation,
      Content content) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.CANNOT_COMPLEMENT,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to answer complement for certificate %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.CANNOT_COMPLEMENT,
              Optional.of(actionEvaluation))
      );
    }

    certificate.answerComplement(actionEvaluation, content);

    setMessagesToHandleDomainService.handle(certificate.messages(MessageType.COMPLEMENT));

    final var latestAnswer = certificate.messages(MessageType.COMPLEMENT).stream()
        .max(Comparator.comparing(Message::created))
        .map(Message::answer)
        .orElseThrow(() -> new IllegalStateException(
            "No answer found for certificate %s".formatted(certificateId)));

    messageEventDomainService.publish(
        MessageEvent.builder()
            .type(MessageEventType.ANSWER_COMPLEMENT)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .messageId(latestAnswer.id())
            .certificateId(certificate.id())
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return certificate;
  }
}
