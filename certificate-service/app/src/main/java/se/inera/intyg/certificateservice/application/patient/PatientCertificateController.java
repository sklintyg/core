package se.inera.intyg.certificateservice.application.patient;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesResponse;
import se.inera.intyg.certificateservice.application.patient.service.GetPatientCertificateService;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patient/certificates")
public class PatientCertificateController {

  private final GetPatientCertificateService getPatientCertificateService;

  @PostMapping
  @PerformanceLogging(eventAction = "retrieve-certificate-list-for-patient", eventType = EVENT_TYPE_ACCESSED)
  public GetPatientCertificatesResponse getPatientCertificates(
      @RequestBody GetPatientCertificatesRequest getPatientCertificatesRequest) {
    return getPatientCertificateService.get(getPatientCertificatesRequest);
  }
}
