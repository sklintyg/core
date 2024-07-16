package se.inera.intyg.certificateservice.application.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQAResponse;
import se.inera.intyg.certificateservice.application.patient.service.validator.GetPatientCertificatesWithQARequestValidator;

@Service
@RequiredArgsConstructor
public class GetPatientCertificatesWithQAService {

  private final GetPatientCertificatesWithQARequestValidator requestValidator;

  public GetPatientCertificatesWithQAResponse get(GetPatientCertificatesWithQARequest request) {
    requestValidator.validate(request);
    return GetPatientCertificatesWithQAResponse.builder().build();
  }
}
