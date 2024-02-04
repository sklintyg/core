package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.user.model.Role;

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
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    if (actionEvaluation.getPatient() == null || actionEvaluation.getUser() == null) {
      return false;
    }
    if (Role.CARE_ADMIN.equals(actionEvaluation.getUser().getRole())
        && actionEvaluation.getPatient().getProtectedPerson().value()) {
      return false;
    }
    return !actionEvaluation.getPatient().getDeceased().value() && !actionEvaluation.getUser()
        .getBlocked().value();
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
