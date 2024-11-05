package se.inera.intyg.certificateservice.domain.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.repository.StatisticsRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;

@ExtendWith(MockitoExtension.class)
class GetUnitStatisticsDomainServiceTest {

  private static final List<HsaId> ISSUED_ON_UNIT_IDS = List.of(new HsaId("unit1"));
  @Mock
  private StatisticsRepository statisticsRepository;
  @Mock
  private CertificateModelRepository certificateModelRepository;
  @InjectMocks
  private GetUnitStatisticsDomainService getUnitStatisticsDomainService;

  @Test
  void shallSetAllowedToViewProtectedPersonToFalseIfUserIsCareAdmin() {
    final ArgumentCaptor<List<CertificateModelId>> captor = ArgumentCaptor.forClass(List.class);

    final var certificateModel = mock(CertificateModel.class);
    final var actionSpecification = CertificateActionSpecification.builder()
        .allowedRolesForProtectedPersons(List.of(Role.DOCTOR))
        .build();

    doReturn(Optional.of(actionSpecification)).when(certificateModel)
        .certificateAction(CertificateActionType.READ);

    doReturn(List.of(certificateModel)).when(certificateModelRepository).findAllActive();

    getUnitStatisticsDomainService.get(Role.CARE_ADMIN, Collections.emptyList());
    verify(statisticsRepository).getStatisticsForUnits(
        eq(Collections.emptyList()),
        captor.capture());

    assertEquals(0, captor.getValue().size());
  }

  @Test
  void shallSetAllowedToViewProtectedPersonToTrueIfUserNotIsCareAdmin() {
    final ArgumentCaptor<List<CertificateModelId>> captor = ArgumentCaptor.forClass(List.class);

    final var certificateModel = mock(CertificateModel.class);
    final var actionSpecification = CertificateActionSpecification.builder()
        .allowedRolesForProtectedPersons(List.of(Role.DOCTOR))
        .build();

    doReturn(Optional.of(actionSpecification)).when(certificateModel)
        .certificateAction(CertificateActionType.READ);

    doReturn(List.of(certificateModel)).when(certificateModelRepository).findAllActive();

    getUnitStatisticsDomainService.get(Role.DOCTOR, Collections.emptyList());
    verify(statisticsRepository).getStatisticsForUnits(
        eq(Collections.emptyList()),
        captor.capture());

    assertEquals(1, captor.getValue().size());
  }

  @Test
  void shallReturnMapOfUnitStatistics() {
    final var expectedUnitStatisticsMap = Map.of(new HsaId("unit1"), new UnitStatistics(1, 4));

    doReturn(expectedUnitStatisticsMap).when(statisticsRepository)
        .getStatisticsForUnits(ISSUED_ON_UNIT_IDS, Collections.emptyList());

    final var actualUnitStatisticsMap = getUnitStatisticsDomainService.get(
        Role.DOCTOR,
        ISSUED_ON_UNIT_IDS
    );

    assertEquals(expectedUnitStatisticsMap, actualUnitStatisticsMap);
  }
}
