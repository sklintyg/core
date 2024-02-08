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
  private static final String DESCRIPTION = "Skapa ett intygsutkast";

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.certificateActionType();
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    if (actionEvaluation.patient() == null || actionEvaluation.user() == null) {
      return false;
    }

    final var evaluated = actionRules.stream()
        .filter(value -> value.evaluate(actionEvaluation))
        .count() == actionRules.size();

    return evaluated && isPatientAlive(actionEvaluation) && isUserNotBlocked(actionEvaluation);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }

  private static boolean isUserNotBlocked(ActionEvaluation actionEvaluation) {
    return !actionEvaluation.user().blocked().value();
  }

  private static boolean isPatientAlive(ActionEvaluation actionEvaluation) {
    return !actionEvaluation.patient().deceased().value();
  }
}
