package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class ActionRuleSign implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {

    return actionEvaluation.filter(
        evaluation -> certificate.filter(cert -> cert.certificateModel().accessToSign(evaluation))
            .isPresent()).isPresent();
  }
}
