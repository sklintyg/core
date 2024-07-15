package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;

public class ActionRuleUserHasAccessScope implements ActionRule {

  private final List<AccessScope> scopes;

  public ActionRuleUserHasAccessScope(List<AccessScope> scopes) {
    this.scopes = scopes;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionEvaluation
        .filter(value -> scopes.contains(value.user().accessScope()))
        .isPresent();
  }
}
