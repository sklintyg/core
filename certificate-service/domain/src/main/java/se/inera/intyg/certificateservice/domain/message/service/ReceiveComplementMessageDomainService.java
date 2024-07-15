package se.inera.intyg.certificateservice.domain.message.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class ReceiveComplementMessageDomainService {

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

    if (!certificate.isCertificateIssuedOnPatient(message.personId())) {
      throw new CertificateActionForbidden(
          "Not allowed to receive complement on certificate for %s, because patient is not matching"
              .formatted(certificate.id().id()),
          List.of("Different patient on certificate and incoming message!")
      );
    }

    if (certificateDoesNotContainProvidedElementId(message, certificate)) {
      throw new IllegalStateException("Certificate '%s' does not contain element with id: '%s' "
          .formatted(certificate.id().id(),
              message.complements().stream()
                  .map(Complement::elementId)
                  .filter(id -> !certificate.certificateModel().elementSpecificationExists(id))
                  .toList()));
    }

    return messageRepository.save(message);
  }

  private static boolean certificateDoesNotContainProvidedElementId(Message message,
      Certificate certificate) {
    return message.complements().stream()
        .noneMatch(complement -> certificate.certificateModel()
            .elementSpecificationExists(complement.elementId()));
  }
}
