package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.SickLeaveConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetSickLeaveCertificateDomainService;

@Service
@RequiredArgsConstructor
public class GetSickLeaveCertificateInternalService {

  private final GetSickLeaveCertificateDomainService getSickLeaveCertificateDomainService;
  private final SickLeaveConverter sickLeaveConverter;

  public GetSickLeaveCertificateInternalResponse get(String certificateId,
      boolean ignoreModelRules) {
    if (certificateId == null || certificateId.isBlank()) {
      throw new IllegalArgumentException("Certificate id cannot be null or empty");
    }

    final var sickLeaveCertificate = getSickLeaveCertificateDomainService.get(
        new CertificateId(certificateId),
        ignoreModelRules
    );

    return GetSickLeaveCertificateInternalResponse.builder()
        .available(sickLeaveCertificate.isPresent())
        .sickLeaveCertificate(
            sickLeaveConverter.toSickLeaveCertificate(sickLeaveCertificate.orElse(null)))
        .build();
  }
}