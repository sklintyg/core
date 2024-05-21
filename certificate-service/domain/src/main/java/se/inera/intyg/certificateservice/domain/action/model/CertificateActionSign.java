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
public class CertificateActionSign implements CertificateAction {

  private static final String SIGN = "Signera intyget";
  private static final String SIGN_AND_SEND = "Signera och skicka";
  private static final String SIGN_AND_SEND_DESCRIPTION = "Intyget skickas direkt till %s.";
  private static final String SIGN_DESCRIPTION = "Intyget signeras.";
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
  public String getName(Optional<Certificate> optionalCertificate) {
    return optionalCertificate.map(
            certificate -> {
              final var hasSignAfterSendAction = isSignAfterSendActionSpecificationPresent(certificate);
              return hasSignAfterSendAction ? SIGN_AND_SEND : SIGN;
            }
        )
        .orElse(SIGN);
  }

  @Override
  public String getDescription(Optional<Certificate> optionalCertificate) {
    return optionalCertificate.map(
            certificate -> {
              final var hasSignAfterSendAction = isSignAfterSendActionSpecificationPresent(certificate);
              return hasSignAfterSendAction
                  ? SIGN_AND_SEND_DESCRIPTION.formatted(
                  certificate.certificateModel().recipient().name())
                  : SIGN_DESCRIPTION;
            }
        )
        .orElse(SIGN_DESCRIPTION);
  }

  private static boolean isSignAfterSendActionSpecificationPresent(Certificate certificate) {
    return certificate.certificateModel()
        .certificateActionSpecifications()
        .stream()
        .anyMatch(specification -> specification.certificateActionType()
            .equals(CertificateActionType.SEND_AFTER_SIGN));
  }
}