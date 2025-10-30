package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@RequiredArgsConstructor
public class GetSickLeaveCertificatesDomainService {

  private final CertificateRepository certificateRepository;

  public List<SickLeaveCertificate> get(CertificatesRequest request) {
    final var sickLeaves = certificateRepository.findByCertificatesRequest(
        request
    );

    return sickLeaves.stream()
        .filter(certificate -> certificate.certificateModel().sickLeaveProvider() != null)
        .map(certificate -> certificate.certificateModel().sickLeaveProvider()
            .build(certificate, false))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }
}

