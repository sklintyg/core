package se.inera.intyg.certificateservice.domain.certificate.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class GetCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public Certificate get(CertificateId certificateId, ActionEvaluation actionEvaluation) {
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.READ, actionEvaluation)) {
      throw new CertificateActionForbidden(
          "Not allowed to read certificate for %s".formatted(certificateId)
      );
    }
    certificate.updateMetadata(actionEvaluation);
    return certificate;
  }
}
