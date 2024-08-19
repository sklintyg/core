package se.inera.intyg.certificateservice.domain.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.repository.StatisticsRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;

@ExtendWith(MockitoExtension.class)
class GetUnitStatisticsDomainServiceTest {

  private static final List<HsaId> ISSUED_ON_UNIT_IDS = List.of(new HsaId("unit1"));
  @Mock
  private StatisticsRepository statisticsRepository;
  @InjectMocks
  private GetUnitStatisticsDomainService getUnitStatisticsDomainService;

  @Test
  void shallSetAllowedToViewProtectedPersonToFalseIfUserIsCareAdmin() {
    final var booleanArgumentCaptor = ArgumentCaptor.forClass(Boolean.class);
    final var actionEvaluation = ActionEvaluation.builder()
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    getUnitStatisticsDomainService.get(actionEvaluation, Collections.emptyList());
    verify(statisticsRepository).getStatisticsForUnits(
        eq(Collections.emptyList()),
        booleanArgumentCaptor.capture());

    assertFalse(booleanArgumentCaptor.getValue());
  }

  @Test
  void shallSetAllowedToViewProtectedPersonToTrueIfUserNotIsCareAdmin() {
    final var booleanArgumentCaptor = ArgumentCaptor.forClass(Boolean.class);
    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .build();

    getUnitStatisticsDomainService.get(actionEvaluation, Collections.emptyList());
    verify(statisticsRepository).getStatisticsForUnits(
        eq(Collections.emptyList()),
        booleanArgumentCaptor.capture());

    assertTrue(booleanArgumentCaptor.getValue());
  }

  @Test
  void shallReturnMapOfUnitStatistics() {
    final var expectedUnitStatisticsMap = Map.of(new HsaId("unit1"), new UnitStatistics(1, 4));
    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .build();

    doReturn(expectedUnitStatisticsMap).when(statisticsRepository)
        .getStatisticsForUnits(ISSUED_ON_UNIT_IDS, true);

    final var actualUnitStatisticsMap = getUnitStatisticsDomainService.get(actionEvaluation,
        ISSUED_ON_UNIT_IDS);

    assertEquals(expectedUnitStatisticsMap, actualUnitStatisticsMap);
  }
}
