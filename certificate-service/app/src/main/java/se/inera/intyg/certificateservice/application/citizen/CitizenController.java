package se.inera.intyg.certificateservice.application.citizen;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.citizen.service.GetCertificateService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/citizen")
public class CitizenController {

  private final CertificateExistsService certificateExistsService;
  private final GetCertificateService getCertificateService;

  @GetMapping("/{certificateId}/exists")
  CertificateExistsResponse findExistingCertificate(
      @PathVariable("certificateId") String certificateId) {
    return certificateExistsService.exist(certificateId);
  }

  @PostMapping("/{certificateId}")
  GetCertificateResponse getCertificate(
      @RequestBody GetCertificateRequest getCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCertificateService.get(getCertificateRequest, certificateId);
  }

}
