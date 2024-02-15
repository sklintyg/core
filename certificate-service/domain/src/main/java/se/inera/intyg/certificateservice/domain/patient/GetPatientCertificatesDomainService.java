package se.inera.intyg.certificateservice.domain.patient;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@RequiredArgsConstructor
public class GetPatientCertificatesDomainService {

  private final CertificateRepository certificateRepository;

  public List<Certificate> get(ActionEvaluation actionEvaluation) {

//    // Is it careunit
//    List<Certificate> certificates = certificateRepository.findByPatientByCareUnit(
//        actionEvaluation.patient(), actionEvaluation.careUnit()
//    );
//
//    // Is it subunit
//    certificates = certificateRepository.findByPatientBySubUnit(
//        actionEvaluation.patient(), actionEvaluation.subUnit()
//    );
//
//    return certificates.stream()
//        .filter(certificate -> certificate.allowTo(CertificateActionType.READ, actionEvaluation))
//        .toList();
    return null;
  }
}
