package se.inera.intyg.certificateservice.domain.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class HandleMessageDomainService {

  private final MessageRepository messageRepository;

  public Message handle(Message message, boolean handle, Certificate certificate,
      ActionEvaluation actionEvaluation) {
    if (!certificate.allowTo(
        CertificateActionType.HANDLE_COMPLEMENT,
        Optional.of(actionEvaluation)
    )) {
      throw new CertificateActionForbidden(
          "Not allowed to handle complement on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.HANDLE_COMPLEMENT, Optional.empty())
      );
    }

    if (handle && message.type() == MessageType.COMPLEMENT) {
      message.handle();
      return messageRepository.save(message);
    }

    return message;
  }
}
