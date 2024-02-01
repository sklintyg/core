package se.inera.intyg.certificateservice.application.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

  private final GetCertificateService getCertificateService;
  private final CreateCertificateService createCertificateService;

  @PostMapping
  CreateCertificateResponse createCertificate(
      @RequestBody CreateCertificateRequest createCertificateRequest) {
    return createCertificateService.create(createCertificateRequest);
  }

  @PostMapping("/{certificateId}")
  GetCertificateResponse getCertificate(
      @RequestBody GetCertificateRequest getCertificateRequest,
      @PathVariable("certificateId") String certificateId) {
    return getCertificateService.get(getCertificateRequest, certificateId);
  }
}
