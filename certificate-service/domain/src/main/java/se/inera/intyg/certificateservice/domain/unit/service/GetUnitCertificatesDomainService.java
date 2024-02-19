package se.inera.intyg.certificateservice.domain.unit.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@RequiredArgsConstructor
public class GetUnitCertificatesDomainService {

  private final CertificateRepository certificateRepository;

  public List<Certificate> get(CertificatesRequest request, ActionEvaluation actionEvaluation) {
    return certificateRepository.findByCertificatesRequest(
            request.apply(actionEvaluation)
        ).stream()
        .filter(certificate -> certificate.allowTo(CertificateActionType.READ, actionEvaluation))
        .toList();
  }
}

