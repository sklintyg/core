package se.inera.intyg.certificateservice.domain.unit.service;

import static se.inera.intyg.certificateservice.domain.common.model.Role.CARE_ADMIN;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.repository.StatisticsRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;

@RequiredArgsConstructor
public class GetUnitStatisticsDomainService {

  private final StatisticsRepository statisticsRepository;

  public Map<HsaId, UnitStatistics> get(ActionEvaluation actionEvaluation,
      List<HsaId> availableUnitIds) {
    final var allowedToViewProtectedPerson = !CARE_ADMIN.equals(actionEvaluation.user().role());
    return getUnitStatistics(availableUnitIds, allowedToViewProtectedPerson);
  }

  private Map<HsaId, UnitStatistics> getUnitStatistics(List<HsaId> availableUnitIds,
      boolean allowedToViewProtectedPerson) {
    return statisticsRepository.getStatisticsForUnits(
        availableUnitIds,
        allowedToViewProtectedPerson
    );
  }
}
