package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface CertificateAction {

  CertificateActionType getType();

  boolean evaluate(Optional<Certificate> certificate, Optional<ActionEvaluation> actionEvaluation);

  List<String> reasonNotAllowed(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation);

  default String getName(Optional<Certificate> certificate) {
    return null;
  }

  default String getDescription(Optional<Certificate> certificate) {
    return null;
  }

  default String getBody(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return null;
  }

  default boolean isEnabled(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return true;
  }

  default boolean include(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return evaluate(certificate, actionEvaluation);
  }
}
