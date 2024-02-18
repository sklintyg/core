package se.inera.intyg.certificateservice.domain.patient.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@RequiredArgsConstructor
public class GetPatientCertificatesDomainService {

  private final CertificateRepository certificateRepository;

  public List<Certificate> get(ActionEvaluation actionEvaluation) {
    return certificateRepository.findByCertificatesRequest(
            certificatesRequest(actionEvaluation)
        )
        .stream()
        .filter(certificate -> certificate.allowTo(CertificateActionType.READ, actionEvaluation))
        .toList();
  }

  private CertificatesRequest certificatesRequest(ActionEvaluation actionEvaluation) {
    if (actionEvaluation.isIssuingUnitCareUnit()) {
      return CertificatesRequest.builder()
          .careUnitId(actionEvaluation.careUnit().hsaId())
          .personId(actionEvaluation.patient().id())
          .statuses(Status.all())
          .build();
    }

    return CertificatesRequest.builder()
        .issuedUnitId(actionEvaluation.subUnit().hsaId())
        .personId(actionEvaluation.patient().id())
        .statuses(Status.all())
        .build();
  }
}

