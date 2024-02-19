package se.inera.intyg.certificateservice.application.unit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoResponse;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesResponse;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitCertificatesInfoService;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitCertificatesService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/unit/certificates")
public class UnitCertificateController {

  private final GetUnitCertificatesService getUnitCertificatesService;
  private final GetUnitCertificatesInfoService getUnitCertificatesInfoService;

  @PostMapping
  public GetUnitCertificatesResponse getUnitCertificates(
      @RequestBody GetUnitCertificatesRequest getUnitCertificatesRequest) {
    return getUnitCertificatesService.get(getUnitCertificatesRequest);
  }

  @PostMapping("/info")
  public GetUnitCertificatesInfoResponse getUnitCertificatesInfo(
      @RequestBody GetUnitCertificatesInfoRequest getUnitCertificatesInfoRequest) {
    return getUnitCertificatesInfoService.get(getUnitCertificatesInfoRequest);
  }
}
