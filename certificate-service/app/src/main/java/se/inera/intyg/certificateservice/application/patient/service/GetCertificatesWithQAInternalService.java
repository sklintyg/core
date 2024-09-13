package se.inera.intyg.certificateservice.application.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQARequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQAResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CertificatesWithQARequestFactory;
import se.inera.intyg.certificateservice.application.patient.service.validator.CertificatesWithQARequestValidator;
import se.inera.intyg.certificateservice.domain.patient.service.GetCertificatesWithQAInternalDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificatesWithQAInternalService {

  private final CertificatesWithQARequestValidator certificatesWithQARequestValidator;
  private final GetCertificatesWithQAInternalDomainService getCertificatesWithQAInternalDomainService;
  private final CertificatesWithQARequestFactory certificatesWithQARequestFactory;

  public CertificatesInternalWithQAResponse get(CertificatesInternalWithQARequest request) {
    certificatesWithQARequestValidator.validate(request);

    final var certificatesWithQARequest = certificatesWithQARequestFactory.create(request);
    final var xml = getCertificatesWithQAInternalDomainService.get(
        certificatesWithQARequest
    );

    return CertificatesInternalWithQAResponse.builder()
        .list(xml.base64())
        .build();
  }
}
