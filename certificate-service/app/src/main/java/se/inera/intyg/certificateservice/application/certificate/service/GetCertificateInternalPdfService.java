package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalPdfResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class GetCertificateInternalPdfService {

  private final CertificateRepository certificateRepository;

  public GetCertificateInternalPdfResponse get(String certificateId) {
    final var certificate = certificateRepository.getById(new CertificateId(certificateId));

    return GetCertificateInternalPdfResponse.builder()
        .build();
  }
}
