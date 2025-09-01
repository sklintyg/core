package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class RevokePlaceholderCertificateInternalService {

  private final CertificateRepository certificateRepository;

  public Certificate revoke(String certificateId) {
    if (certificateId == null || certificateId.isBlank()) {
      throw new IllegalArgumentException("Certificate id cannot be null or empty");
    }

    final var certificate = certificateRepository.getPlaceholderById(
        new CertificateId(certificateId)
    );

    certificate.revoke(null, null);

    return certificateRepository.save(certificate);
  }
}