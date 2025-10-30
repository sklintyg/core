package se.inera.intyg.certificateservice.testability.certificate;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.infrastructure.errorutil.OptimisticLockErrorHandler;
import se.inera.intyg.certificateservice.testability.certificate.dto.GetCertificateTypeVersionsResponse;
import se.inera.intyg.certificateservice.testability.certificate.dto.SupportedCertificateTypesResponse;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityCertificateRequest;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityResetCertificateRequest;
import se.inera.intyg.certificateservice.testability.certificate.service.TestabilityCertificateService;

@Profile(TESTABILITY_PROFILE)
@RequiredArgsConstructor
@RestController
@RequestMapping("/testability/certificate")
public class TestabilityCertificateController {

  private final TestabilityCertificateService testabilityCertificateService;

  @PostMapping
  @OptimisticLockErrorHandler
  CreateCertificateResponse createCertificate(
      @RequestBody TestabilityCertificateRequest testabilityCertificateRequest) {
    return testabilityCertificateService.create(testabilityCertificateRequest);
  }

  @DeleteMapping
  void reset(@RequestBody TestabilityResetCertificateRequest testabilityResetCertificateRequest) {
    testabilityCertificateService.reset(testabilityResetCertificateRequest);
  }

  @GetMapping("/types")
  List<SupportedCertificateTypesResponse> getSupportedTypes() {
    return testabilityCertificateService.supportedTypes();
  }

  @GetMapping("/types/{type}")
  GetCertificateTypeVersionsResponse getCertificateTypeVersions(@PathVariable String type) {
    return testabilityCertificateService.getCertificateTypeVersions(type);
  }
}
