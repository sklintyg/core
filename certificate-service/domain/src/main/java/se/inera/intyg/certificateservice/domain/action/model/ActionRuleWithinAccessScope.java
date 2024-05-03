package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;

public class ActionRuleWithinAccessScope implements ActionRule {

  private final AccessScope accessScope;

  public ActionRuleWithinAccessScope(AccessScope accessScope) {
    this.accessScope = accessScope;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    final var scope = getStrictestAccessScope(actionEvaluation);

    switch (scope) {
      case WITHIN_CARE_UNIT -> {
        return certificate
            .filter(value ->
                isIssuingUnitMatchingSubUnit(actionEvaluation, value)
                    || isCareUnitMatchingSubUnit(actionEvaluation, value)
            )
            .isPresent();
      }
      case WITHIN_CARE_PROVIDER -> {
        return certificate
            .filter(value ->
                isWithinCareProvider(actionEvaluation, value)
            )
            .isPresent();
      }
      case ALL_CARE_PROVIDERS -> {
        return true;
      }
    }

    return false;
  }

  private AccessScope getStrictestAccessScope(ActionEvaluation actionEvaluation) {
    final var userAccessScope = actionEvaluation.user().accessScope();

    if (accessScope == null || userAccessScope == null) {
      return AccessScope.WITHIN_CARE_UNIT;
    }

    if (accessScope == AccessScope.WITHIN_CARE_UNIT
        || userAccessScope == AccessScope.WITHIN_CARE_UNIT) {
      return AccessScope.WITHIN_CARE_UNIT;
    }

    if (accessScope == AccessScope.WITHIN_CARE_PROVIDER
        || userAccessScope == AccessScope.WITHIN_CARE_PROVIDER) {
      return AccessScope.WITHIN_CARE_PROVIDER;
    }

    return AccessScope.ALL_CARE_PROVIDERS;
  }

  private static boolean isWithinCareProvider(ActionEvaluation actionEvaluation,
      Certificate value) {
    return value.certificateMetaData().careProvider().hsaId().equals(
        actionEvaluation.careProvider().hsaId()
    );
  }

  private static boolean isCareUnitMatchingSubUnit(ActionEvaluation actionEvaluation,
      Certificate value) {
    return value.certificateMetaData().careUnit().hsaId().equals(
        actionEvaluation.subUnit().hsaId()
    );
  }

  private static boolean isIssuingUnitMatchingSubUnit(ActionEvaluation actionEvaluation,
      Certificate value) {
    return value.certificateMetaData().issuingUnit().hsaId().equals(
        actionEvaluation.subUnit().hsaId()
    );
  }
}
