package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.Role;

public class ActionRuleRole implements ActionRule {

  private final List<Role> roles;

  public ActionRuleRole(List<Role> roles) {
    this.roles = roles;
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionEvaluation.filter(evaluation -> roles.contains(evaluation.user().role()))
        .isPresent();
  }
}
