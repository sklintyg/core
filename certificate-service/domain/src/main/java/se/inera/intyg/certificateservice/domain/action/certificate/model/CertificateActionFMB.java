package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

@Builder
@Getter(AccessLevel.NONE)
public class CertificateActionFMB implements CertificateAction {

  private final CertificateActionSpecification certificateActionSpecification;
  private final List<ActionRule> actionRules;

  private static final String NAME = "FMB";
  private static final String DESCRIPTION = "Läs FMB - ett stöd för ifyllnad och bedömning.";

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.certificateActionType();
  }

  @Override
  public List<String> reasonNotAllowed(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionRules.stream()
        .filter(value -> !value.evaluate(certificate, actionEvaluation))
        .map(actionRule -> actionRule.getReasonForPermissionDenied(certificate))
        .toList();
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionRules.stream()
        .filter(value -> value.evaluate(certificate, actionEvaluation))
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