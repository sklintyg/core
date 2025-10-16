package se.inera.intyg.certificateservice.domain.certificate.service;

import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.READ;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class GetCertificateCandidateDomainService {

  private final CertificateRepository certificateRepository;

  public Optional<Certificate> get(CertificateId certificateId, ActionEvaluation actionEvaluation) {

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(READ, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to read certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(READ, Optional.of(actionEvaluation))
      );
    }

    final var candidateCertificate = certificate.candidateForUpdate();
    if (candidateCertificate.isEmpty()) {
      return Optional.empty();
    }

    if (!candidateCertificate.get().allowTo(READ, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to get candidate certificate %s for %s"
              .formatted(candidateCertificate.get().id(), certificateId),
          certificate.reasonNotAllowed(READ, Optional.of(actionEvaluation))
      );
    }

    return candidateCertificate;
  }
}
