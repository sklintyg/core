package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class ActionRuleUserAllowCopy implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    return actionEvaluation.user().allowCopy().value();
  }
}