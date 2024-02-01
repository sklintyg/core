package se.inera.intyg.certificateservice.domain.action.model;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

public class CertificateActionRead implements CertificateAction {

  private final CertificateActionSpecification certificateActionSpecification;

  public CertificateActionRead(CertificateActionSpecification certificateActionSpecification) {
    this.certificateActionSpecification = certificateActionSpecification;
  }

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.getCertificateActionType();
  }

  @Override
  public boolean evaluate(ActionEvaluation actionEvaluation) {
    return !actionEvaluation.getUser().getBlocked().value() && !actionEvaluation.getSubUnit()
        .getInactive().value();
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
