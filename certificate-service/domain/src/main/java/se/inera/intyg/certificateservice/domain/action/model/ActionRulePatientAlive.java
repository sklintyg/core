package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class ActionRulePatientAlive implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    return !actionEvaluation.patient().deceased().value();
  }

  @Override
  public String getErrorMessage() {
    return "Du saknar behörighet för den begärda åtgärden."
        + " Det krävs särskilda rättigheter eller en specifik befattning"
        + " för att hantera avlidna patienter.";
  }
}
