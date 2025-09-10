package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class GetSickLeaveCertificateInternalService {

  private final CertificateRepository certificateRepository;
  private final CertificateConverter certificateConverter;

  public GetSickLeaveCertificateInternalResponse get(String certificateId) {
    if (certificateId == null || certificateId.isBlank()) {
      throw new IllegalArgumentException("Certificate id cannot be null or empty");
    }

    final var certificate = certificateRepository.getById(new CertificateId(certificateId));
    certificate.certificateModel().sickLeaveEnabled();
    return GetSickLeaveCertificateInternalResponse.builder()
        .certificate(null) //FIXME: Implement sick leave certificate conversion
        .build();
  }

}
