package se.inera.intyg.certificateservice.domain.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@RequiredArgsConstructor
public class CreateMessageDomainService {

  private final MessageRepository messageRepository;

  public Message create(Certificate certificate, ActionEvaluation actionEvaluation,
      MessageType messageType, Content content) {
    if (!certificate.allowTo(CertificateActionType.CREATE_MESSAGE,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to create messages on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.CREATE_MESSAGE, Optional.empty())
      );
    }

    final var createdMessage = Message.create(
        messageType,
        content,
        certificate.id(),
        Staff.create(actionEvaluation.user())
    );

    return messageRepository.save(createdMessage);
  }
}
