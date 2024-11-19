package se.inera.intyg.certificateservice.application.certificatetypeinfo;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateTypeVersionResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.GetCertificateTypeInfoService;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.GetLatestCertificateExternalTypeVersionService;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.GetLatestCertificateTypeVersionService;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/certificatetypeinfo")
public class CertificateTypeInfoController {

  private final GetCertificateTypeInfoService getCertificateTypeInfoService;
  private final GetLatestCertificateTypeVersionService getLatestCertificateTypeVersionService;
  private final GetLatestCertificateExternalTypeVersionService getLatestCertificateExternalTypeVersionService;

  @PostMapping
  @PerformanceLogging(eventAction = "find-active-certificate-types", eventType = EVENT_TYPE_ACCESSED)
  GetCertificateTypeInfoResponse findActiveCertificateTypeInfos(
      @RequestBody GetCertificateTypeInfoRequest getCertificateTypeInfoRequest) {
    return getCertificateTypeInfoService.getActiveCertificateTypeInfos(
        getCertificateTypeInfoRequest);
  }

  @GetMapping("/{type}/exists")
  @PerformanceLogging(eventAction = "find-existing-certificate-type", eventType = EVENT_TYPE_ACCESSED)
  GetLatestCertificateTypeVersionResponse findLatestCertificateTypeVersion(
      @PathVariable("type") String type) {
    return getLatestCertificateTypeVersionService.get(type);
  }

  @GetMapping("/{codeSystem}/{code}/exists")
  @PerformanceLogging(eventAction = "find-latest-certificate-type-version", eventType = EVENT_TYPE_ACCESSED)
  GetLatestCertificateExternalTypeVersionResponse findLatestCertificateExternalTypeVersion(
      @PathVariable("codeSystem") String codeSystem,
      @PathVariable("code") String code) {
    return getLatestCertificateExternalTypeVersionService.get(codeSystem, code);
  }
}
