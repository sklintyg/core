package se.inera.intyg.certificateservice.domain.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class ReceiveMessageDomainService {

  private final CertificateRepository certificateRepository;
  private final MessageRepository messageRepository;

  public Message receive(Message message) {
    final var certificate = certificateRepository.getById(message.certificateId());
    if (!certificate.allowTo(CertificateActionType.RECEIVE_COMPLEMENT, Optional.empty())) {
      throw new CertificateActionForbidden(
          "Not allowed to receive complement on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.RECEIVE_COMPLEMENT, Optional.empty())
      );
    }

    return messageRepository.save(message);
  }
}
