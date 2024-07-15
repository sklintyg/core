package se.inera.intyg.certificateservice.domain.message.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.Reminder;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class ReceiveReminderMessageDomainService {

  private final CertificateRepository certificateRepository;
  private final MessageRepository messageRepository;

  public Message receive(MessageId messageId, Reminder reminder) {
    final var message = messageRepository.getById(messageId);
    final var certificate = certificateRepository.getById(message.certificateId());
    if (!certificate.allowTo(CertificateActionType.RECEIVE_REMINDER, Optional.empty())) {
      throw new CertificateActionForbidden(
          "Not allowed to receive reminder on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.RECEIVE_REMINDER, Optional.empty())
      );
    }

    if (!certificate.isCertificateIssuedOnPatient(message.personId())) {
      throw new CertificateActionForbidden(
          "Not allowed to receive complement on certificate for %s, because patient is not matching"
              .formatted(certificate.id().id()),
          List.of("Different patient on certificate and incoming message!")
      );
    }

    message.remind(reminder);

    return messageRepository.save(message);
  }
}
