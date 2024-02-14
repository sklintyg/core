package se.inera.intyg.certificateservice.domain.certificate.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@RequiredArgsConstructor
public class DeleteCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public void delete(CertificateId certificateId, Revision revision,
      ActionEvaluation actionEvaluation) {
    final var certificate = certificateRepository.getById(certificateId);
    if (true) {
      // Validera och kasta exception
    }

    certificate.delete(revision);
    
    certificateRepository.save(certificate);
  }
}
