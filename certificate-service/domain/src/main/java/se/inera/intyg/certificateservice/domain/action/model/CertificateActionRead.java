package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
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
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    if (certificate.isEmpty()) {
      throw new IllegalArgumentException("Missing required parameter certificate");
    }
    return certificate.get().certificateMetaData().getIssuingUnit().getHsaId()
        .equals(actionEvaluation.getSubUnit().getHsaId());
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
