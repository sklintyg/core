package se.inera.intyg.certificateservice.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CertificateActionCreate implements CertificateAction {

  private final CertificateActionSpecification certificateActionSpecification;

  public CertificateActionCreate(CertificateActionSpecification certificateActionSpecification) {
    this.certificateActionSpecification = certificateActionSpecification;
  }

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.getCertificateActionType();
  }

  @Override
  public boolean evaluate(ActionEvaluation actionEvaluation) {
    return false;
  }
}
