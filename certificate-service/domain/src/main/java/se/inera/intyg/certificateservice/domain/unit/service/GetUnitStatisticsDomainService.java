package se.inera.intyg.certificateservice.domain.unit.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.repository.StatisticsRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;

@RequiredArgsConstructor
public class GetUnitStatisticsDomainService {

  private final StatisticsRepository statisticsRepository;
  private final CertificateModelRepository certificateModelRepository;

  public Map<HsaId, UnitStatistics> get(Role userRole, List<HsaId> issuedByUnitIds) {
    final var typesToViewProtectedPersons = certificateModelRepository.findAllActive().stream()
        .filter(certificateModel ->
            certificateModel.certificateAction(CertificateActionType.READ)
                .map(actionSpecification ->
                    actionSpecification.allowedRolesForProtectedPersons().contains(userRole)
                )
                .orElse(false)
        )
        .map(CertificateModel::id)
        .toList();

    return statisticsRepository.getStatisticsForUnits(issuedByUnitIds, typesToViewProtectedPersons);
  }
}
