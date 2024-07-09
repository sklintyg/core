package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class ForwardMessageCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public Certificate forward(CertificateId certificateId, ActionEvaluation actionEvaluation) {
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.FORWARD_MESSAGE,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to forward messages for certificate %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.FORWARD_MESSAGE,
              Optional.of(actionEvaluation))
      );
    }

    return certificate;
  }
}
