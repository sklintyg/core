package se.inera.intyg.certificateservice.application.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalMetadataService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalXmlService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internalapi/certificate")
public class CertificateInternalApiController {

  private final GetCertificateInternalXmlService getCertificateInternalXmlService;
  private final GetCertificateInternalMetadataService getCertificateInternalMetadataService;
  private final GetCertificateInternalService getCertificateInternalService;
  private final CertificateExistsService certificateExistsService;

  @GetMapping("/{certificateId}/exists")
  CertificateExistsResponse findExistingCertificate(
      @PathVariable("certificateId") String certificateId) {
    return certificateExistsService.exist(certificateId);
  }

  @PostMapping("/{certificateId}/xml")
  GetCertificateInternalXmlResponse getCertificateXml(
      @PathVariable("certificateId") String certificateId) {
    return getCertificateInternalXmlService.get(certificateId);
  }

  @GetMapping("/{certificateId}/metadata")
  GetCertificateInternalMetadataResponse getCertificateMetadata(
      @PathVariable("certificateId") String certificateId) {
    return getCertificateInternalMetadataService.get(certificateId);
  }

  @PostMapping("/{certificateId}")
  GetCertificateInternalResponse getCertificate(
      @PathVariable("certificateId") String certificateId) {
    return getCertificateInternalService.get(certificateId);
  }
}
