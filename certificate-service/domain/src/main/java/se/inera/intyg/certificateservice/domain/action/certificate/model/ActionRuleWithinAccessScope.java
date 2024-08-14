package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;

public class ActionRuleWithinAccessScope implements ActionRule {

  private final AccessScope accessScope;

  public ActionRuleWithinAccessScope(AccessScope accessScope) {
    this.accessScope = accessScope;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    if (actionEvaluation.isEmpty()) {
      return false;
    }

    final var scope = getStrictestAccessScope(actionEvaluation.get());

    switch (scope) {
      case WITHIN_CARE_UNIT -> {
        return certificate
            .filter(value ->
                value.isWithinCareUnit(actionEvaluation.get())
            )
            .isPresent();
      }
      case WITHIN_CARE_PROVIDER -> {
        return certificate
            .filter(value ->
                value.isWithinCareProvider(actionEvaluation.get())
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
}
