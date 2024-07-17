package se.inera.intyg.certificateservice.application.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.patient.CertificatesWithQARequestFactory;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQAResponse;
import se.inera.intyg.certificateservice.application.patient.service.validator.GetPatientCertificatesWithQARequestValidator;
import se.inera.intyg.certificateservice.domain.patient.service.GetPatientCertificatesWithQADomainService;

@Service
@RequiredArgsConstructor
public class GetPatientCertificatesWithQAService {

  private final GetPatientCertificatesWithQARequestValidator requestValidator;
  private final GetPatientCertificatesWithQADomainService getPatientCertificatesWithQADomainService;
  private final CertificatesWithQARequestFactory certificatesWithQARequestFactory;

  public GetPatientCertificatesWithQAResponse get(GetPatientCertificatesWithQARequest request) {
    requestValidator.validate(request);
    final var certificatesWithQARequest = certificatesWithQARequestFactory.create(request);
    final var certificatesXml = getPatientCertificatesWithQADomainService.get(
        certificatesWithQARequest
    );

    return GetPatientCertificatesWithQAResponse.builder()
        .list(certificatesXml.base64())
        .build();
  }
}
