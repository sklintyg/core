package se.inera.intyg.certificateservice.model;

public interface CertificateAction {

  CertificateActionType getType();

  boolean evaluate(ActionEvaluation actionEvaluation);

  String getName();

  String getDescription();
}
