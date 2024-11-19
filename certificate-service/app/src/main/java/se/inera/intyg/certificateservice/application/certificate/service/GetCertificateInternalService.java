package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class GetCertificateInternalService {

  private final CertificateRepository certificateRepository;
  private final CertificateConverter certificateConverter;

  public GetCertificateInternalResponse get(String certificateId) {
    if (certificateId == null || certificateId.isBlank()) {
      throw new IllegalArgumentException("Certificate id cannot be null or empty");
    }

    final var certificate = certificateRepository.getById(new CertificateId(certificateId));
    return GetCertificateInternalResponse.builder()
        .certificate(
            certificateConverter.convert(
                certificate,
                Collections.emptyList(),
                null
            )
        )
        .build();
  }
}
