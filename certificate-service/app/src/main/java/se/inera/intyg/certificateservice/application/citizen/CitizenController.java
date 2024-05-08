package se.inera.intyg.certificateservice.application.citizen;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.citizen.dto.CitizenCertificateExistsResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.service.CitizenCertificateExistsService;
import se.inera.intyg.certificateservice.application.citizen.service.GetCitizenCertificateService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/citizen")
public class CitizenController {

  private final CitizenCertificateExistsService citizenCertificateExistsService;
  private final GetCitizenCertificateService getCitizenCertificateService;

  @GetMapping("/{certificateId}/exists")
  CitizenCertificateExistsResponse findExistingCertificate(
      @PathVariable("certificateId") String certificateId) {
    return citizenCertificateExistsService.exist(certificateId);
  }

  @PostMapping("/{certificateId}")
  GetCitizenCertificateResponse getCertificate(
      @RequestBody GetCitizenCertificateRequest getCitizenCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCitizenCertificateService.get(getCitizenCertificateRequest, certificateId);
  }

}
