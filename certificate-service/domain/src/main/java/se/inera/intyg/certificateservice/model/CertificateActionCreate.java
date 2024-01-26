package se.inera.intyg.certificateservice.model;

public class CertificateActionCreate implements CertificateAction {

  private static final String NAME = "Skapa intyg";
  private static final String DESCRIPTION = "Skapa ett intygsutkast";
  private static final CertificateActionType TYPE = CertificateActionType.CREATE;

  @Override
  public CertificateActionType getType() {
    return TYPE;
  }

  @Override
  public boolean evaluate(ActionEvaluation actionEvaluation) {
    return !actionEvaluation.getPatient().getDeceased();
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
