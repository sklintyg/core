package se.inera.intyg.certificateservice.domain.certificatemodel.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionRuleUnitAccess;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateUnitAccessEvaluationRepository;

@RequiredArgsConstructor
public class ListAvailableCertificateModelsDomainService {

  private final CertificateModelRepository certificateModelRepository;
  private final CertificateUnitAccessEvaluationRepository certificateUnitAccessEvaluationRepository;

  public List<CertificateModel> get(ActionEvaluation actionEvaluation) {
    final var certificateModels = certificateModelRepository.findAllActive();
    return certificateModels.stream()
        .filter(roleHasAccess(actionEvaluation))
        .filter(model -> unitAccessEvaluation(model, actionEvaluation))
        .toList();
  }

  private boolean unitAccessEvaluation(CertificateModel certificateModel,
      ActionEvaluation actionEvaluation) {
    final var actionRuleUnitAccess = new ActionRuleUnitAccess(
        certificateUnitAccessEvaluationRepository
    );

    return actionRuleUnitAccess.evaluate(certificateModel.id().type(), actionEvaluation);
  }

  private static Predicate<CertificateModel> roleHasAccess(
      ActionEvaluation actionEvaluation) {
    return certificateModel -> certificateModel.allowTo(CertificateActionType.LIST_CERTIFICATE_TYPE,
        Optional.of(actionEvaluation));
  }
}
