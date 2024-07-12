package se.inera.intyg.certificateservice.domain.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
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
    if (message.type() == MessageType.COMPLEMENT) {
      return handleComplementMessage(message, handle, certificate, actionEvaluation);
    }

    return handleAdministrativeMessage(message, handle, certificate, actionEvaluation);
  }

  private Message handleAdministrativeMessage(Message message, boolean handle,
      Certificate certificate,
      ActionEvaluation actionEvaluation) {
    if (!certificate.allowTo(
        CertificateActionType.HANDLE_MESSAGE,
        Optional.of(actionEvaluation)
    )) {
      throw new CertificateActionForbidden(
          "Not allowed to handle administrative message on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.HANDLE_MESSAGE, Optional.empty())
      );
    }

    if (handle) {
      deleteDraftAnswerIfExists(message);
      message.handle();
    } else {
      message.unhandle();
    }

    return messageRepository.save(message);
  }

  private static void deleteDraftAnswerIfExists(Message message) {
    if (message.answer() != null && message.answer().isDraft()) {
      message.deleteAnswer();
    }
  }

  private Message handleComplementMessage(Message message, boolean handle, Certificate certificate,
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
