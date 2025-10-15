package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class GetCertificateCandidateDomainService {

  private final CertificateRepository certificateRepository;

  public Optional<Certificate> get(CertificateId certificateId, ActionEvaluation actionEvaluation) {

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.READ, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to read certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.READ, Optional.of(actionEvaluation))
      );
    }

    if (!certificate.allowTo(CertificateActionType.UPDATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to get candidate certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.UPDATE_DRAFT_FROM_CERTIFICATE,
              Optional.of(actionEvaluation))
      );
    }

    return certificate.candidateForUpdate();
  }
}
