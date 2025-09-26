package se.inera.intyg.certificateservice.domain.message.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class ReceiveQuestionMessageDomainService {

  private final CertificateRepository certificateRepository;
  private final MessageRepository messageRepository;

  public Message receive(Message message) {
    final var certificate = certificateRepository.getById(message.certificateId());
    if (!certificate.allowTo(CertificateActionType.RECEIVE_QUESTION, Optional.empty())) {
      throw new CertificateActionForbidden(
          "Not allowed to receive question message on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.RECEIVE_QUESTION, Optional.empty())
      );
    }

    if (!certificate.isCertificateIssuedOnPatient(message.personId())) {
      throw new CertificateActionForbidden(
          "Not allowed to receive question on certificate for %s, because patient is not matching"
              .formatted(certificate.id().id()),
          List.of("Different patient on certificate and incoming message!")
      );
    }

    final var isAllowedMessageType = certificate.certificateModel().messageTypes().stream()
        .anyMatch(type -> type.type().equals(message.type()));
    if (!isAllowedMessageType) {
      throw new CertificateActionForbidden(
          "Not allowed to receive message of type %s on certificate for %s"
              .formatted(message.type(), certificate.id().id()),
          List.of(
              "Message type %s not allowed on certificate model %s"
                  .formatted(message.type(), certificate.certificateModel().id())
          )
      );
    }

    return messageRepository.save(message);
  }
}
