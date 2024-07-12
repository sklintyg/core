package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@RequiredArgsConstructor
public class SetCertificatesToLockedDomainService {

  private final CertificateRepository certificateRepository;

  public void lock(List<Certificate> certificates) {
    certificates.forEach(Certificate::lock);
    certificates.forEach(certificateRepository::save);
  }
}
