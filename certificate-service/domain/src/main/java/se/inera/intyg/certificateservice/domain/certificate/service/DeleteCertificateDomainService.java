package se.inera.intyg.certificateservice.domain.certificate.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class DeleteCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public Certificate delete(CertificateId certificateId, Revision revision,
      ActionEvaluation actionEvaluation) {
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.DELETE, actionEvaluation)) {
      throw new CertificateActionForbidden(
          "Not allowed to delete certificate for %s".formatted(certificateId)
      );
    }

    certificate.updateMetadata(actionEvaluation);
    certificate.delete(revision);
    
    return certificateRepository.save(certificate);
  }
}
