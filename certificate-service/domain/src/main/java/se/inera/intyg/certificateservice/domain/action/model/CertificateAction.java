package se.inera.intyg.certificateservice.domain.action.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface CertificateAction {

  CertificateActionType getType();

  default boolean evaluate(ActionEvaluation actionEvaluation) {
    return evaluate(Optional.empty(), actionEvaluation);
  }

  default List<String> reasonNotAllowed(ActionEvaluation actionEvaluation) {
    return reasonNotAllowed(Optional.empty(), actionEvaluation);
  }

  List<String> reasonNotAllowed(Optional<Certificate> certificate,
      ActionEvaluation actionEvaluation);

  boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation);

  default String getName() {
    return null;
  }

  default String getDescription() {
    return null;
  }

  default String getBody(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    return null;
  }

  default boolean isEnabled() {
    return true;
  }
}
