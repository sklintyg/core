package se.inera.intyg.certificateservice.domain.action.model;

public interface CertificateAction {

  CertificateActionType getType();

  boolean evaluate(ActionEvaluation actionEvaluation);

  String getName();

  String getDescription();

  default boolean isEnabled() {
    return true;
  }
}
