package se.inera.intyg.certificateservice.domain.certificate.repository;

import java.util.List;
import java.util.Map;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;

public interface StatisticsRepository {

  Map<String, UnitStatistics> getStatisticsForUnits(List<String> unitIds,
      boolean allowedToViewProtectedPerson);
}
