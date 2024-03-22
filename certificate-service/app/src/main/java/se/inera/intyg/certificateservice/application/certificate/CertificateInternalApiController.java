package se.inera.intyg.certificateservice.application.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalPdfResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalPdfService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateInternalXmlService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internalapi/certificate")
public class CertificateInternalApiController {

  private final GetCertificateInternalXmlService getCertificateInternalXmlService;
  private final GetCertificateInternalPdfService getCertificateInternalPdfService;

  @PostMapping("/{certificateId}/xml")
  GetCertificateInternalXmlResponse getCertificateXml(
      @PathVariable("certificateId") String certificateId) {
    return getCertificateInternalXmlService.get(certificateId);
  }

  @PostMapping("{certificateId}/pdf")
  GetCertificateInternalPdfResponse getCertificatePdf(
      @PathVariable("certificateId") String certificateId) {
    return getCertificateInternalPdfService.get(certificateId);
  }

}
