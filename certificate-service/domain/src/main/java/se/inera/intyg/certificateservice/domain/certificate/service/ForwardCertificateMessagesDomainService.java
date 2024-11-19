package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class ForwardCertificateMessagesDomainService {

  private final MessageRepository messageRepository;

  public Certificate forward(Certificate certificate, ActionEvaluation actionEvaluation) {
    if (!certificate.allowTo(CertificateActionType.FORWARD_MESSAGE,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to forward messages for certificate %s".formatted(certificate.id()),
          certificate.reasonNotAllowed(CertificateActionType.FORWARD_MESSAGE,
              Optional.of(actionEvaluation))
      );
    }

    certificate.forwardMessages();

    certificate.messages().forEach(messageRepository::save);

    return certificate;
  }
}
