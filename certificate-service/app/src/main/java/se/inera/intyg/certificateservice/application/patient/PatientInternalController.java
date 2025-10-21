package se.inera.intyg.certificateservice.application.patient;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.service.GetSickLeaveCertificatesInternalService;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internalapi/patient")
public class PatientInternalController {

  private final GetSickLeaveCertificatesInternalService getSickLeaveCertificatesInternalService;

  @PostMapping("sickleave")
  @PerformanceLogging(eventAction = "internal-retrieve-sickleave-certificates", eventType = EVENT_TYPE_ACCESSED)
  GetSickLeaveCertificatesInternalResponse getSickLeaveCertificates(
      @RequestBody GetSickLeaveCertificatesInternalRequest request) {
    return getSickLeaveCertificatesInternalService.get(request);
  }
}
