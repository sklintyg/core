package se.inera.intyg.certificateservice.domain.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class DeleteMessageDomainService {

  private final MessageRepository messageRepository;
  private final CertificateRepository certificateRepository;

  public void delete(MessageId messageId, ActionEvaluation actionEvaluation) {
    final var message = messageRepository.getById(messageId);
    final var certificate = certificateRepository.getById(message.certificateId());
    if (!certificate.allowTo(CertificateActionType.DELETE_MESSAGE,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to delete messages on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.DELETE_MESSAGE, Optional.empty())
      );
    }

    message.delete();

    messageRepository.save(message);
  }
}
