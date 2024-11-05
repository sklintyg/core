package se.inera.intyg.certificateservice.domain.certificate.repository;

import java.util.List;
import java.util.Map;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;

public interface StatisticsRepository {

  Map<HsaId, UnitStatistics> getStatisticsForUnits(List<HsaId> issuedByUnitIds,
      List<CertificateModelId> allowedToViewProtectedPerson);
}
