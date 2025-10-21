package se.inera.intyg.certificateservice.domain.patient.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
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
        .filter(certificate -> certificate.allowTo(CertificateActionType.READ,
            Optional.of(actionEvaluation)))
        .toList();
  }

  private CertificatesRequest certificatesRequest(ActionEvaluation actionEvaluation) {
    if (actionEvaluation.isIssuingUnitCareUnit()) {
      return CertificatesRequest.builder()
          .careUnitId(actionEvaluation.careUnit().hsaId())
          .personId(actionEvaluation.patient().id())
          .build();
    }

    return CertificatesRequest.builder()
        .issuedUnitIds(List.of(actionEvaluation.subUnit().hsaId()))
        .personId(actionEvaluation.patient().id())
        .build();
  }
}

