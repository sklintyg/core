package se.inera.intyg.certificateservice.application.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalMetadataService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalXmlService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internalapi/certificate")
public class CertificateInternalApiController {

  private final GetCertificateInternalXmlService getCertificateInternalXmlService;
  private final GetCertificateInternalMetadataService getCertificateInternalMetadataService;

  @PostMapping("/{certificateId}/xml")
  GetCertificateInternalXmlResponse getCertificateXml(
      @PathVariable("certificateId") String certificateId) {
    return getCertificateInternalXmlService.get(certificateId);
  }

  @PostMapping("/{certificateId}/metadata")
  GetCertificateInternalMetadataResponse getCertificateMetadata(
      @PathVariable("certificateId") String certificateId) {
    return getCertificateInternalMetadataService.get(certificateId);
  }
}
