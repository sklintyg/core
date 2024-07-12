package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface ActionRule {

  boolean evaluate(Optional<Certificate> certificate, Optional<ActionEvaluation> actionEvaluation);

  default String getReasonForPermissionDenied() {
    return "Du saknar behörighet för den begärda åtgärden."
        + " För att utföra denna uppgift krävs särskilda rättigheter eller en specifik befattning.";
  }
}
