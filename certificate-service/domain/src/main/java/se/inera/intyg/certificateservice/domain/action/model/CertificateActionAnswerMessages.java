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
public class CertificateActionAnswerMessages implements CertificateAction {

  private static final String NAME = "Svara";
  private static final String DESCRIPTION = "Svara på fråga";
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
  public String getName(Optional<Certificate> optionalCertificate) {
    return NAME;
  }

  @Override
  public String getDescription(Optional<Certificate> optionalCertificate) {
    return DESCRIPTION;
  }

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.certificateActionType();
  }

  @Override
  public boolean evaluate(Optional<Certificate> optionalCertificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionRules.stream()
        .filter(value -> value.evaluate(optionalCertificate, actionEvaluation))
        .count() == actionRules.size();
  }
}