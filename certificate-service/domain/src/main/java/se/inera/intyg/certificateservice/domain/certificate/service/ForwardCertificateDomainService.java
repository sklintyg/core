package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class ForwardCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public Certificate forward(Certificate certificate, ActionEvaluation actionEvaluation) {
    if (!certificate.allowTo(CertificateActionType.FORWARD_CERTIFICATE,
        Optional.of(actionEvaluation)) && !certificate.allowTo(
        CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to forward certificate %s".formatted(certificate.id()),
          certificate.reasonNotAllowed(CertificateActionType.FORWARD_CERTIFICATE,
              Optional.of(actionEvaluation))
      );
    }

    certificate.forward();

    return certificateRepository.save(certificate);
  }
}
