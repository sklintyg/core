package se.inera.intyg.certificateservice.application.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.patient.CertificatesWithQARequestFactory;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQAResponse;
import se.inera.intyg.certificateservice.application.patient.service.validator.GetPatientCertificatesWithQARequestValidator;
import se.inera.intyg.certificateservice.domain.patient.service.GetPatientCertificatesWithQAInternalDomainService;

@Service
@RequiredArgsConstructor
public class GetPatientCertificatesWithQAInternalService {

  private final GetPatientCertificatesWithQARequestValidator requestValidator;
  private final GetPatientCertificatesWithQAInternalDomainService getPatientCertificatesWithQAInternalDomainService;
  private final CertificatesWithQARequestFactory certificatesWithQARequestFactory;

  public PatientCertificatesWithQAResponse get(PatientCertificatesWithQARequest request) {
    requestValidator.validate(request);

    final var certificatesWithQARequest = certificatesWithQARequestFactory.create(request);
    final var xml = getPatientCertificatesWithQAInternalDomainService.get(
        certificatesWithQARequest
    );

    return PatientCertificatesWithQAResponse.builder()
        .list(xml.base64())
        .build();
  }
}
