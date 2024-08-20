package se.inera.intyg.certificateservice.domain.unit.service;

import static se.inera.intyg.certificateservice.domain.common.model.Role.CARE_ADMIN;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.repository.StatisticsRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;

@RequiredArgsConstructor
public class GetUnitStatisticsDomainService {

  private final StatisticsRepository statisticsRepository;

  public Map<HsaId, UnitStatistics> get(Role userRole, List<HsaId> issuedByUnitIds) {
    final var allowedToViewProtectedPerson = !CARE_ADMIN.equals(userRole);
    return getUnitStatistics(issuedByUnitIds, allowedToViewProtectedPerson);
  }

  private Map<HsaId, UnitStatistics> getUnitStatistics(List<HsaId> issuedByUnitIds,
      boolean allowedToViewProtectedPerson) {
    return statisticsRepository.getStatisticsForUnits(
        issuedByUnitIds,
        allowedToViewProtectedPerson
    );
  }
}
