package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

@Builder
@Getter(AccessLevel.NONE)
public class CertificateActionMessages implements CertificateAction {

  private static final String NAME = "Ärendekommunikation";
  private static final String DESCRIPTION = "Hantera kompletteringsbegäran, frågor och svar";
  private final CertificateActionSpecification certificateActionSpecification;
  private final List<ActionRule> actionRules;

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.certificateActionType();
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    if (isDraftButIsComplementing(certificate)) {
      return true;
    }

    return actionRules.stream()
        .filter(value -> value.evaluate(certificate, actionEvaluation))
        .count() == actionRules.size();
  }

  @Override
  public List<String> reasonNotAllowed(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionRules.stream()
        .filter(value -> !value.evaluate(certificate, actionEvaluation))
        .map(ActionRule::getReasonForPermissionDenied)
        .toList();
  }

  @Override
  public String getName(Optional<Certificate> optionalCertificate) {
    return NAME;
  }

  @Override
  public String getDescription(Optional<Certificate> optionalCertificate) {
    return DESCRIPTION;
  }

  private static boolean isDraftButIsComplementing(Optional<Certificate> certificate) {
    return certificate.stream()
        .filter(value -> Status.DRAFT.equals(value.status()))
        .filter(value -> value.parent() != null)
        .anyMatch(value -> RelationType.COMPLEMENT.equals(value.parent().type()));
  }
}