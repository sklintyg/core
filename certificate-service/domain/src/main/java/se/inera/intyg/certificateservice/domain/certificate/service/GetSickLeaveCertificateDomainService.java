package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@RequiredArgsConstructor
public class GetSickLeaveCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public Optional<SickLeaveCertificate> get(CertificateId certificateId) {
    Certificate certificate;
    try {
      certificate = certificateRepository.getById(certificateId);
    } catch (Exception e) {
      return Optional.empty();
    }
    if (certificate.certificateModel().sickLeaveProvider() == null) {
      return Optional.empty();
    }

    final var sickLeave = certificate.certificateModel().sickLeaveProvider().build(certificate);

    if (sickLeave.isPresent() && !sickLeave.get().partOfSickLeaveChain()) {
      return Optional.empty();
    }

    return sickLeave;
  }
}