package se.inera.intyg.certificateservice.application.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsDTO;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsResponse;
import se.inera.intyg.certificateservice.application.unit.service.validator.UnitStatisticsRequestValidator;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitStatisticsDomainService;

@ExtendWith(MockitoExtension.class)
class GetUnitStatisticsServiceTest {

  private static final List<String> AVAILABLE_UNIT_IDS = List.of("unit1", "unit2");
  private static final List<HsaId> AVAILABLE_UNIT_HSA_IDS = List.of(new HsaId("unit1"),
      new HsaId("unit2"));
  private static final String UNIT_1 = "unit1";
  private static final String UNIT_2 = "unit2";
  @Mock
  private UnitStatisticsRequestValidator unitStatisticsRequestValidator;
  @Mock
  private GetUnitStatisticsDomainService getUnitStatisticsDomainService;
  @InjectMocks
  private GetUnitStatisticsService getUnitStatisticsService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = UnitStatisticsRequest.builder().build();
    doThrow(IllegalArgumentException.class).when(unitStatisticsRequestValidator).validate(request);

    assertThrows(IllegalArgumentException.class, () -> getUnitStatisticsService.get(request));
  }

  @Test
  void shallReturnUnitStatisticsResponse() {
    final var expectedResult = UnitStatisticsResponse.builder()
        .unitStatistics(
            Map.of(
                UNIT_1,
                UnitStatisticsDTO.builder()
                    .draftCount(5)
                    .unhandledMessageCount(5)
                    .build(),
                UNIT_2,
                UnitStatisticsDTO.builder()
                    .draftCount(5)
                    .unhandledMessageCount(5)
                    .build()
            )
        )
        .build();

    final var statisticsMap = Map.of(
        new HsaId(UNIT_1), new UnitStatistics(5, 5),
        new HsaId(UNIT_2), new UnitStatistics(5, 5)
    );

    doReturn(statisticsMap).when(getUnitStatisticsDomainService).get(
        AJLA_DOCTOR_DTO.getRole().toRole(),
        AVAILABLE_UNIT_HSA_IDS);

    final var actualResult = getUnitStatisticsService.get(
        UnitStatisticsRequest.builder()
            .user(AJLA_DOCTOR_DTO)
            .issuedByUnitIds(AVAILABLE_UNIT_IDS)
            .build()
    );

    assertEquals(expectedResult, actualResult);
  }
}
