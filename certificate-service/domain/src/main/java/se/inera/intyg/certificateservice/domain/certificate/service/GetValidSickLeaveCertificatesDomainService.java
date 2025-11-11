package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@RequiredArgsConstructor
public class GetValidSickLeaveCertificatesDomainService {

  private final CertificateRepository certificateRepository;

  public List<Certificate> get(List<CertificateId> certificateId) {
    final var validSickLeavesByIds = certificateRepository.findValidSickLeavesByIds(certificateId);
    
    return validSickLeavesByIds.stream()
        .filter(certificate -> certificate.certificateModel().sickLeaveProvider() != null)
        .toList();
  }
}