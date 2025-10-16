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
public class CertificateActionUpdateDraftFromCertificate implements CertificateAction {

  private final CertificateActionSpecification certificateActionSpecification;
  private final List<ActionRule> actionRules;

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.certificateActionType();
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
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    final var result = actionRules.stream()
        .filter(value -> value.evaluate(certificate, actionEvaluation))
        .count() == actionRules.size();

    return result && certificate.map(cert -> cert.candidateForUpdate().isPresent()).orElse(false);
  }

  @Override
  public String getName(Optional<Certificate> certificate) {
    return certificateActionSpecification.contentProvider().name(certificate.orElse(null));
  }

  @Override
  public String getDescription(Optional<Certificate> certificate) {
    return certificateActionSpecification.contentProvider().description(certificate.orElse(null));
  }

  @Override
  public String getBody(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificateActionSpecification.contentProvider().body(certificate.orElse(null));
  }
}