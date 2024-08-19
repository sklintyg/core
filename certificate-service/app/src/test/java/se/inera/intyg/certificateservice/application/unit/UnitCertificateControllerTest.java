package se.inera.intyg.certificateservice.application.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoResponse;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesResponse;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsDTO;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsResponse;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitCertificatesInfoService;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitCertificatesService;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitStatisticsService;

@ExtendWith(MockitoExtension.class)
class UnitCertificateControllerTest {

  private static final String UNIT = "unit";
  @Mock
  private GetUnitStatisticsService getUnitStatisticsService;
  @Mock
  private GetUnitCertificatesService getUnitCertificatesService;
  @Mock
  private GetUnitCertificatesInfoService getUnitCertificatesInfoService;
  @InjectMocks
  private UnitCertificateController unitCertificateController;

  @Test
  void shallReturnGetUnitCertificatesResponse() {
    final var certificates = List.of(CertificateDTO.builder().build());
    final var expectedResult = GetUnitCertificatesResponse.builder()
        .certificates(certificates)
        .build();

    final var request = GetUnitCertificatesRequest.builder().build();
    doReturn(expectedResult).when(getUnitCertificatesService).get(request);

    final var actualResult = unitCertificateController.getUnitCertificates(request);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetUnitCertificatesInfoResponse() {
    final var staffs = List.of(StaffDTO.builder().build());
    final var expectedResult = GetUnitCertificatesInfoResponse.builder()
        .staffs(staffs)
        .build();

    final var request = GetUnitCertificatesInfoRequest.builder().build();
    doReturn(expectedResult).when(getUnitCertificatesInfoService).get(request);

    final var actualResult = unitCertificateController.getUnitCertificatesInfo(request);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnUnitStatisticsResponse() {
    final var expectedResult = UnitStatisticsResponse.builder()
        .unitStatistics(Map.of(UNIT, UnitStatisticsDTO.builder().build()))
        .build();

    final var request = UnitStatisticsRequest.builder().build();
    doReturn(expectedResult).when(getUnitStatisticsService).get(request);

    final var actualResult = unitCertificateController.getUnitStatistics(request);
    assertEquals(expectedResult, actualResult);
  }
}
