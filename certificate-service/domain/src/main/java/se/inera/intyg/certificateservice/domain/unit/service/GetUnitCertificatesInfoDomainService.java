package se.inera.intyg.certificateservice.domain.unit.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@RequiredArgsConstructor
public class GetUnitCertificatesInfoDomainService {

  private final CertificateRepository certificateRepository;

  public List<Staff> get(CertificatesRequest request, ActionEvaluation actionEvaluation) {
    return certificateRepository.findByCertificatesRequest(
            request.apply(actionEvaluation)
        ).stream()
        .map(certificate -> certificate.certificateMetaData().issuer())
        .distinct()
        .toList();
  }
}

