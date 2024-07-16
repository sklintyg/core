package se.inera.intyg.certificateservice.application.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQAResponse;
import se.inera.intyg.certificateservice.application.patient.service.GetPatientCertificatesWithQAService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internalapi/patient/certificates")
public class PatientCertificateInternalApiController {

  GetPatientCertificatesWithQAService getPatientCertificatesWithQAService;

  @PostMapping("/qa")
  GetPatientCertificatesWithQAResponse getPatientCertificatesWithQA(
      @RequestBody GetPatientCertificatesWithQARequest request) {
    return getPatientCertificatesWithQAService.get(request);
  }
}
