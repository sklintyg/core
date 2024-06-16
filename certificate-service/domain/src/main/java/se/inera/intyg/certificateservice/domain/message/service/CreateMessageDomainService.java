package se.inera.intyg.certificateservice.domain.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@RequiredArgsConstructor
public class CreateMessageDomainService {

  private final MessageRepository messageRepository;
  private final CertificateRepository certificateRepository;

  public Message create(CertificateId certificateId, ActionEvaluation actionEvaluation,
      MessageType messageType, Content content) {
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.CREATE_MESSAGES,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to create messages on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.CREATE_MESSAGES, Optional.empty())
      );
    }

    final var createdMessage = Message.create(
        messageType,
        content,
        new Author(Staff.create(actionEvaluation.user()).name().fullName()),
        certificateId
    );

    return messageRepository.save(createdMessage);
  }
}
