package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface CertificateAction {

  CertificateActionType getType();

  default boolean evaluate(ActionEvaluation actionEvaluation) {
    return evaluate(Optional.empty(), actionEvaluation);
  }

  boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation);

  String getName();

  String getDescription();

  default boolean isEnabled() {
    return true;
  }
}
