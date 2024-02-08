package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface ActionRule {

  default boolean evaluate(ActionEvaluation actionEvaluation) {
    return evaluate(Optional.empty(), actionEvaluation);
  }

  boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation);
}
