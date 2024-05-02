package se.inera.intyg.certificateservice.domain.action.model;

import java.util.List;
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
    return getUserAccessScope(actionEvaluation).contains(accessScope);
  }

  private List<AccessScope> getUserAccessScope(ActionEvaluation actionEvaluation) {
    final var userAccessScope = actionEvaluation.user().accessScope();

    if (userAccessScope == null) {
      return List.of(
          AccessScope.WITHIN_CARE_UNIT);
    }

    switch (userAccessScope) {
      case ALL_CARE_PROVIDERS -> {
        return List.of(
            AccessScope.ALL_CARE_PROVIDERS,
            AccessScope.WITHIN_CARE_PROVIDER,
            AccessScope.WITHIN_CARE_UNIT);
      }
      case WITHIN_CARE_PROVIDER -> {
        return List.of(
            AccessScope.WITHIN_CARE_PROVIDER,
            AccessScope.WITHIN_CARE_UNIT);
      }
      default -> {
        return List.of(
            AccessScope.WITHIN_CARE_UNIT);
      }
    }
  }
}