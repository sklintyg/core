package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class CertificateExistsService {

  private final CertificateRepository certificateRepository;

  public CertificateExistsResponse exist(String certificateId) {
    if (certificateId == null || certificateId.isBlank()) {
      throw new IllegalArgumentException(
          "Required parameter certificateId missing: '%s'".formatted(certificateId)
      );
    }
    return CertificateExistsResponse.builder()
        .exists(
            certificateRepository.exists(new CertificateId(certificateId))
        )
        .build();
  }
}
