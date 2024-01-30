package se.inera.intyg.certificateservice.application.certificatetypeinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateTypeVersionResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.CertificateTypeInfoService;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.GetLatestCertificateTypeVersionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/certificatetypeinfo")
public class CertificateTypeInfoController {

  private final CertificateTypeInfoService certificateTypeInfoService;
  private final GetLatestCertificateTypeVersionService getLatestCertificateTypeVersionService;

  @PostMapping
  GetCertificateTypeInfoResponse findActiveCertificateTypeInfos(
      @RequestBody GetCertificateTypeInfoRequest getCertificateTypeInfoRequest) {
    return certificateTypeInfoService.getActiveCertificateTypeInfos(getCertificateTypeInfoRequest);
  }

  @GetMapping("/{type}/exists")
  GetLatestCertificateTypeVersionResponse findLatestCertificateTypeVersion(
      @PathVariable("type") String type) {
    return getLatestCertificateTypeVersionService.get(type);
  }
}
