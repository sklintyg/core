package se.inera.intyg.certificateservice.application.citizen;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_CHANGE;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.citizen.dto.CitizenCertificateExistsResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.service.CitizenCertificateExistsService;
import se.inera.intyg.certificateservice.application.citizen.service.GetCitizenCertificateListService;
import se.inera.intyg.certificateservice.application.citizen.service.GetCitizenCertificateService;
import se.inera.intyg.certificateservice.application.citizen.service.PrintCitizenCertificateService;
import se.inera.intyg.certificateservice.application.citizen.service.SendCitizenCertificateService;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/citizen")
public class CitizenController {

  private final CitizenCertificateExistsService citizenCertificateExistsService;
  private final GetCitizenCertificateService getCitizenCertificateService;
  private final GetCitizenCertificateListService getCitizenCertificateListService;
  private final PrintCitizenCertificateService printCitizenCertificateService;
  private final SendCitizenCertificateService sendCitizenCertificateService;

  @GetMapping("/certificate/{certificateId}/exists")
  @PerformanceLogging(eventAction = "citizen-find-existing-certificate", eventType = EVENT_TYPE_ACCESSED)
  CitizenCertificateExistsResponse findExistingCertificate(
      @PathVariable("certificateId") String certificateId) {
    return citizenCertificateExistsService.exist(certificateId);
  }

  @PostMapping("/certificate/{certificateId}")
  @PerformanceLogging(eventAction = "citizen-retrieve-certificate", eventType = EVENT_TYPE_ACCESSED)
  GetCitizenCertificateResponse getCertificate(
      @RequestBody GetCitizenCertificateRequest getCitizenCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCitizenCertificateService.get(getCitizenCertificateRequest, certificateId);
  }

  @PostMapping("/certificate")
  @PerformanceLogging(eventAction = "citizen-retrieve-certificate-list", eventType = EVENT_TYPE_ACCESSED)
  GetCitizenCertificateListResponse getCertificateList(
      @RequestBody GetCitizenCertificateListRequest getCitizenCertificateListRequest) {
    return getCitizenCertificateListService.get(getCitizenCertificateListRequest);
  }

  @PostMapping("/certificate/{certificateId}/print")
  @PerformanceLogging(eventAction = "citizen-retrieve-certificate-pdf", eventType = EVENT_TYPE_ACCESSED)
  PrintCitizenCertificateResponse printCertificate(
      @RequestBody PrintCitizenCertificateRequest printCitizenCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return printCitizenCertificateService.get(printCitizenCertificateRequest, certificateId);
  }

  @PostMapping("/certificate/{certificateId}/send")
  @PerformanceLogging(eventAction = "citizen-send-certificate", eventType = EVENT_TYPE_CHANGE)
  SendCitizenCertificateResponse sendCertificate(
      @RequestBody SendCitizenCertificateRequest sendCitizenCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return sendCitizenCertificateService.send(sendCitizenCertificateRequest, certificateId);
  }
}