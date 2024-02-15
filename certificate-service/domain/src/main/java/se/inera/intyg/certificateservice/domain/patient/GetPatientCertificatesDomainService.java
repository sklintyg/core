package se.inera.intyg.certificateservice.domain.patient;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@RequiredArgsConstructor
public class GetPatientCertificatesDomainService {

  private final CertificateRepository certificateRepository;

  public List<Certificate> get(ActionEvaluation actionEvaluation) {
    return getPatientCertificates(actionEvaluation).stream()
        .filter(certificate -> certificate.allowTo(CertificateActionType.READ, actionEvaluation))
        .toList();
  }

  private List<Certificate> getPatientCertificates(ActionEvaluation actionEvaluation) {
    if (actionEvaluation.isIssuingUnitCareUnit(actionEvaluation.subUnit(),
        actionEvaluation.careUnit())) {
      return certificateRepository.findByPatientByCareUnit(actionEvaluation.patient(),
          actionEvaluation.careUnit());
    }
    return certificateRepository.findByPatientBySubUnit(actionEvaluation.patient(),
        actionEvaluation.subUnit());
  }
}

