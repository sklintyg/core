package se.inera.intyg.certificateservice.application.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

  private final CreateCertificateService createCertificateService;
  private final CertificateExistsService certificateExistsService;

  @PostMapping
  CreateCertificateResponse createCertificate(
      @RequestBody CreateCertificateRequest createCertificateRequest) {
    return createCertificateService.create(createCertificateRequest);
  }

  @GetMapping("/{certificateId}/exists")
  CertificateExistsResponse findExistingCertificate(
      @PathVariable("certificateId") String certificateId) {
    return certificateExistsService.exist(certificateId);
  }
}
