package se.inera.intyg.certificateservice.application.certificateexternaltypeinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto.GetLatestCertificateExternalTypeVersionRequest;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.service.GetLatestCertificateExternalTypeVersionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/certificateexternaltypeinfo")
public class CertificateExternalTypeInfoController {

  private final GetLatestCertificateExternalTypeVersionService getLatestCertificateExternalTypeVersionService;


  @PostMapping("/exists")
  GetLatestCertificateExternalTypeVersionResponse findLatestCertificateExternalTypeVersion(
      @RequestBody GetLatestCertificateExternalTypeVersionRequest request) {
    return getLatestCertificateExternalTypeVersionService.get(request);
  }
}
