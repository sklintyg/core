package se.inera.intyg.certificateservice.domain.patient.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest;

@RequiredArgsConstructor
public class GetPatientCertificatesWithQADomainService {

  private final CertificateRepository certificateRepository;

  public List<Certificate> get(CertificatesWithQARequest request) {
    return certificateRepository.findByCertificatesWithQARequest(
        request
    );
  }
}

