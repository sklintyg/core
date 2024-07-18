package se.inera.intyg.certificateservice.application.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQAResponse;
import se.inera.intyg.certificateservice.application.patient.service.GetPatientCertificatesWithQAInternalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internalapi/patient/certificates")
public class PatientCertificateInternalApiController {

  private final GetPatientCertificatesWithQAInternalService getPatientCertificatesWithQAInternalService;

  @PostMapping("/qa")
  PatientCertificatesWithQAResponse getPatientCertificatesWithQA(
      @RequestBody PatientCertificatesWithQARequest request) {
    return getPatientCertificatesWithQAInternalService.get(request);
  }
}
