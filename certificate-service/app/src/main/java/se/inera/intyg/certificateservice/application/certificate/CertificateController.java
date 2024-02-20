package se.inera.intyg.certificateservice.application.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.DeleteCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.UpdateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.ValidateCertificateService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

  private final GetCertificateService getCertificateService;
  private final CreateCertificateService createCertificateService;
  private final CertificateExistsService certificateExistsService;
  private final UpdateCertificateService updateCertificateService;
  private final DeleteCertificateService deleteCertificateService;
  private final ValidateCertificateService validateCertificateService;

  @PostMapping
  CreateCertificateResponse createCertificate(
      @RequestBody CreateCertificateRequest createCertificateRequest) {
    return createCertificateService.create(createCertificateRequest);
  }

  @GetMapping("/{certificateId}/exists")
  CertificateExistsResponse findExistingCertificate(
      @PathVariable String certificateId) {
    return certificateExistsService.exist(certificateId);
  }

  @PostMapping("/{certificateId}")
  GetCertificateResponse getCertificate(
      @RequestBody GetCertificateRequest getCertificateRequest,
      @PathVariable String certificateId) {
    return getCertificateService.get(getCertificateRequest, certificateId);
  }

  @PutMapping("/{certificateId}")
  UpdateCertificateResponse updateCertificate(
      @RequestBody UpdateCertificateRequest updateCertificateRequest,
      @PathVariable String certificateId) {
    return updateCertificateService.update(updateCertificateRequest, certificateId);
  }

  @DeleteMapping("/{certificateId}/{version}")
  DeleteCertificateResponse deleteCertificate(
      @RequestBody DeleteCertificateRequest deleteCertificateRequest,
      @PathVariable("certificateId") String certificateId, @PathVariable("version") Long version) {
    return deleteCertificateService.delete(deleteCertificateRequest, certificateId, version);
  }

  @PostMapping("/{certificateId}/validate")
  ValidateCertificateResponse validateCertificate(
      @RequestBody ValidateCertificateRequest validateCertificateRequest,
      @PathVariable String certificateId) {
    return validateCertificateService.validate(validateCertificateRequest, certificateId);
  }
}
