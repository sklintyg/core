package se.inera.intyg.certificateservice.domain.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@RequiredArgsConstructor
public class SaveMessageDomainService {

  private final MessageRepository messageRepository;

  public Message save(Certificate certificate, MessageId messageId,
      Content content, ActionEvaluation actionEvaluation, MessageType messageType) {
    if (!certificate.allowTo(CertificateActionType.SAVE_MESSAGE,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to save messages on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.SAVE_MESSAGE, Optional.empty())
      );
    }

    final var message = messageRepository.getById(messageId);

    message.update(
        content,
        messageType,
        Staff.create(actionEvaluation.user()),
        new Subject(messageType.displayName())
    );

    return messageRepository.save(message);
  }
}
