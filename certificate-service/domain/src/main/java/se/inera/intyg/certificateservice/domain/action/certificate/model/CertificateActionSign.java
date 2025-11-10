package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

@Builder
@Getter(AccessLevel.NONE)
public class CertificateActionSign implements CertificateAction {

  private static final String SIGN_NAME = "Signera intyget";
  private static final String SEND_AFTER_SIGN_NAME = "Signera och skicka";
  private static final String SEND_AFTER_SIGN_DESCRIPTION = "Intyget skickas direkt till %s.";
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
            certificate -> displaySendAfterSignAction(certificate) ? SEND_AFTER_SIGN_NAME : SIGN_NAME
        )
        .orElse(SIGN_NAME);
  }

  @Override
  public String getDescription(Optional<Certificate> optionalCertificate) {
    return optionalCertificate.filter(CertificateActionSign::displaySendAfterSignAction)
        .map(
            certificate -> SEND_AFTER_SIGN_DESCRIPTION.formatted(
                certificate.certificateModel().recipient().name())
        )
        .orElse(SIGN_DESCRIPTION);
  }

  private static boolean displaySendAfterSignAction(Certificate certificate) {
    return certificate.certificateModel()
        .certificateActionExists(CertificateActionType.SEND_AFTER_SIGN)
        || hasComplementRelationAndSendAfterComplementActionAction(certificate);
  }

  private static boolean hasComplementRelationAndSendAfterComplementActionAction(
      Certificate certificate) {
    return certificate.parent() != null
        && RelationType.COMPLEMENT.equals(certificate.parent().type())
        && certificate.certificateModel()
        .certificateActionExists(CertificateActionType.SEND_AFTER_COMPLEMENT);
  }
}