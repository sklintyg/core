package se.inera.intyg.certificateservice.domain.action.model;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

public class CertificateActionRead implements CertificateAction {

  private final CertificateActionSpecification certificateActionSpecification;

  public CertificateActionRead(CertificateActionSpecification certificateActionSpecification) {
    this.certificateActionSpecification = certificateActionSpecification;
  }

  @Override
  public CertificateActionType getType() {
    return null;
  }

  @Override
  public boolean evaluate(ActionEvaluation actionEvaluation) {
    return false;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public String getDescription() {
    return null;
  }
}
