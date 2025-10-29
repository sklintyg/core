package se.inera.intyg.certificateservice.domain.certificatemodel.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionRuleCertificateTypeActiveForUnit;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;

@RequiredArgsConstructor
public class ListAvailableCertificateModelsDomainService {

  private final CertificateModelRepository certificateModelRepository;
  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  public List<CertificateModel> get(ActionEvaluation actionEvaluation) {
    final var certificateModels = certificateModelRepository.findAllActive();
    return certificateModels.stream()
        .filter(roleHasAccess(actionEvaluation))
        .filter(model -> unitAccessEvaluation(model, actionEvaluation))
        .toList();
  }

  public List<CertificateModel> getLatestVersions(ActionEvaluation actionEvaluation) {
    final var certificateModels = certificateModelRepository.findAllActive();
    return certificateModels.stream()
        .filter(roleHasAccess(actionEvaluation))
        .filter(model -> unitAccessEvaluation(model, actionEvaluation))
        .collect(Collectors.groupingBy(
            model -> model.id().type(),
            Collectors.maxBy(
                Comparator.comparing(model -> model.id().version().version())
            )
        ))
        .values()
        .stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  private boolean unitAccessEvaluation(CertificateModel certificateModel,
      ActionEvaluation actionEvaluation) {
    final var actionRuleUnitAccess = new ActionRuleCertificateTypeActiveForUnit(
        certificateActionConfigurationRepository
    );

    return actionRuleUnitAccess.evaluate(certificateModel.id().type(), actionEvaluation);
  }

  private static Predicate<CertificateModel> roleHasAccess(
      ActionEvaluation actionEvaluation) {
    return certificateModel -> certificateModel.allowTo(CertificateActionType.LIST_CERTIFICATE_TYPE,
        Optional.of(actionEvaluation));
  }
}
