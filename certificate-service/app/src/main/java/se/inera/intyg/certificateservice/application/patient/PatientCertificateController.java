package se.inera.intyg.certificateservice.application.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesResponse;
import se.inera.intyg.certificateservice.application.patient.service.GetPatientCertificateService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patient/certificate")
public class PatientCertificateController {

  private final GetPatientCertificateService getPatientCertificateService;

  @PostMapping
  public GetPatientCertificatesResponse getPatientCertificates(
      @RequestBody GetPatientCertificatesRequest getPatientCertificatesRequest) {
    return getPatientCertificateService.get(getPatientCertificatesRequest);
  }
}
