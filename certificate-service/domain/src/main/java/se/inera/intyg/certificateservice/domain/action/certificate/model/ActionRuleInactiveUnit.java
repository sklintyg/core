package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class ActionRuleInactiveUnit implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionEvaluation.filter(evaluation -> !evaluation.subUnit().inactive().value())
        .isPresent();
  }
}
