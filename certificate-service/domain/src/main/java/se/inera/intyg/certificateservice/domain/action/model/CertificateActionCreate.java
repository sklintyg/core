package se.inera.intyg.certificateservice.domain.action.model;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

public class CertificateActionCreate implements CertificateAction {

  private final CertificateActionSpecification certificateActionSpecification;

  private static final String NAME = "Skapa intyg";
  private static final String DESCRIPTION = "Skapa ett intygsutkast";

  public CertificateActionCreate(CertificateActionSpecification certificateActionSpecification) {
    this.certificateActionSpecification = certificateActionSpecification;
  }

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.getCertificateActionType();
  }

  @Override
  public boolean evaluate(ActionEvaluation actionEvaluation) {
    return !actionEvaluation.getPatient().getDeceased().deceased() && !actionEvaluation.getUser()
        .isBlocked();
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
