package se.inera.intyg.certificateservice.domain.action.model;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

@Builder
@Getter(AccessLevel.NONE)
public class CertificateActionCreate implements CertificateAction {

  private final CertificateActionSpecification certificateActionSpecification;
  private final List<ActionRule> actionRules;

  private static final String NAME = "Skapa intyg";
  private static final String DESCRIPTION = "Skapa ett intygsutkast.";

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.certificateActionType();
  }

  @Override
  public List<String> reasonNotAllowed(Optional<Certificate> certificate,
      ActionEvaluation actionEvaluation) {
    return actionRules.stream()
        .filter(value -> !value.evaluate(actionEvaluation))
        .map(ActionRule::getReasonForPermissionDenied)
        .toList();
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    if (actionEvaluation.patient() == null || actionEvaluation.user() == null) {
      return false;
    }

    return actionRules.stream()
        .filter(value -> value.evaluate(actionEvaluation))
        .count() == actionRules.size();
  }

  @Override
  public String getName(Optional<Certificate> certificate) {
    return NAME;
  }

  @Override
  public String getDescription(Optional<Certificate> certificate) {
    return DESCRIPTION;
  }
}
