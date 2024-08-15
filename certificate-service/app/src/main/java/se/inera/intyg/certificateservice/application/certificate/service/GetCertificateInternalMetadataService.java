package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateMetadataConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class GetCertificateInternalMetadataService {

  private final CertificateRepository certificateRepository;
  private final CertificateMetadataConverter certificateMetadataConverter;

  public GetCertificateInternalMetadataResponse get(String certificateId) {
    if (certificateId == null || certificateId.isBlank()) {
      throw new IllegalArgumentException("Certificate id cannot be null or empty");
    }

    final var certificate = certificateRepository.getById(new CertificateId(certificateId));
    return GetCertificateInternalMetadataResponse.builder()
        .certificateMetadata(certificateMetadataConverter.convert(certificate, null))
        .build();
  }
}
