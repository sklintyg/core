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
public class CertificateActionReplace implements CertificateAction {

  private static final String NAME = "Ersätt";
  private static final String DESCRIPTION = "Skapar en kopia av detta intyg som du kan redigera.";
  private static final String UNHANDLED_COMPLEMENT_MESSAGE_DESCRIPTION = "Intyget har minst en ohanterad kompletteringsbegäran och går inte att ersätta.";
  private final CertificateActionSpecification certificateActionSpecification;
  private final List<ActionRule> actionRules;

  @Override
  public List<String> reasonNotAllowed(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionRules.stream()
        .filter(value -> !value.evaluate(certificate, actionEvaluation))
        .map(ActionRule::getReasonForPermissionDenied)
        .toList();
  }

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.certificateActionType();
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
    final var complementMessageRule = actionRules.stream()
        .filter(ActionRuleNoComplementMessages.class::isInstance)
        .anyMatch(rule -> !rule.evaluate(certificate, Optional.empty()));

    if (complementMessageRule) {
      return UNHANDLED_COMPLEMENT_MESSAGE_DESCRIPTION;
    }
    return DESCRIPTION;
  }

  @Override
  public boolean isEnabled(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return evaluate(certificate, actionEvaluation);
  }

  @Override
  public boolean include(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    final var includeRules = actionRules.stream()
        .filter(rule -> !(rule instanceof ActionRuleNoComplementMessages))
        .toList();

    return includeRules.stream()
        .filter(value -> value.evaluate(certificate, actionEvaluation))
        .count() == includeRules.size();
  }
}