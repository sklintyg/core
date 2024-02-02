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
    return certificate.filter(value ->
        isIssuingUnitMatchingSubUnit(actionEvaluation, value) ||
            isCareUnitMatchingSubUnit(actionEvaluation, value)
    ).isPresent();
  }

  private static boolean isCareUnitMatchingSubUnit(ActionEvaluation actionEvaluation,
      Certificate value) {
    return value.certificateMetaData()
        .getCareUnit().getHsaId().equals(actionEvaluation.getSubUnit().getHsaId());
  }

  private static boolean isIssuingUnitMatchingSubUnit(ActionEvaluation actionEvaluation,
      Certificate value) {
    return value.certificateMetaData().getIssuingUnit().getHsaId()
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
