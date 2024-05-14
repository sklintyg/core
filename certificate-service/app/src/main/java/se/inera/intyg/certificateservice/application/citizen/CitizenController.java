package se.inera.intyg.certificateservice.application.citizen;

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
import se.inera.intyg.certificateservice.application.citizen.service.CitizenCertificateExistsService;
import se.inera.intyg.certificateservice.application.citizen.service.GetCitizenCertificateListService;
import se.inera.intyg.certificateservice.application.citizen.service.GetCitizenCertificateService;
import se.inera.intyg.certificateservice.application.citizen.service.PrintCitizenCertificateService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/citizen")
public class CitizenController {

  private final CitizenCertificateExistsService citizenCertificateExistsService;
  private final GetCitizenCertificateService getCitizenCertificateService;
  private final GetCitizenCertificateListService getCitizenCertificateListService;
  private final PrintCitizenCertificateService printCitizenCertificateService;

  @GetMapping("/certificate/{certificateId}/exists")
  CitizenCertificateExistsResponse findExistingCertificate(
      @PathVariable("certificateId") String certificateId) {
    return citizenCertificateExistsService.exist(certificateId);
  }

  @PostMapping("/certificate/{certificateId}")
  GetCitizenCertificateResponse getCertificate(
      @RequestBody GetCitizenCertificateRequest getCitizenCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCitizenCertificateService.get(getCitizenCertificateRequest, certificateId);
  }

  @PostMapping("/certificate")
  GetCitizenCertificateListResponse getCertificateList(
      @RequestBody GetCitizenCertificateListRequest getCitizenCertificateListRequest) {
    return getCitizenCertificateListService.get(getCitizenCertificateListRequest);
  }

  @PostMapping("/certificate/{certificateId}/print")
  PrintCitizenCertificateResponse printCertificate(
      @RequestBody PrintCitizenCertificateRequest printCitizenCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return printCitizenCertificateService.get(printCitizenCertificateRequest, certificateId);
  }

}
