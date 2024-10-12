package se.inera.intyg.certificateservice.application.unit;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoResponse;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesResponse;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsResponse;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitCertificatesInfoService;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitCertificatesService;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitStatisticsService;
import se.inera.intyg.certificateservice.logging.PerformanceLogging;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/unit/certificates")
public class UnitCertificateController {

  private final GetUnitCertificatesService getUnitCertificatesService;
  private final GetUnitCertificatesInfoService getUnitCertificatesInfoService;
  private final GetUnitStatisticsService getUnitStatisticsService;

  @PostMapping
  @PerformanceLogging(eventAction = "retrieve-certificate-list-for-unit", eventType = EVENT_TYPE_ACCESSED)
  public GetUnitCertificatesResponse getUnitCertificates(
      @RequestBody GetUnitCertificatesRequest getUnitCertificatesRequest) {
    return getUnitCertificatesService.get(getUnitCertificatesRequest);
  }

  @PostMapping("/info")
  @PerformanceLogging(eventAction = "retrieve-certificate-info-list-for-unit", eventType = EVENT_TYPE_ACCESSED)
  public GetUnitCertificatesInfoResponse getUnitCertificatesInfo(
      @RequestBody GetUnitCertificatesInfoRequest getUnitCertificatesInfoRequest) {
    return getUnitCertificatesInfoService.get(getUnitCertificatesInfoRequest);
  }

  @PostMapping("/statistics")
  @PerformanceLogging(eventAction = "retrieve-unit-statistics", eventType = EVENT_TYPE_ACCESSED)
  public UnitStatisticsResponse getUnitStatistics(
      @RequestBody UnitStatisticsRequest unitStatisticsRequest) {
    return getUnitStatisticsService.get(unitStatisticsRequest);
  }
}
