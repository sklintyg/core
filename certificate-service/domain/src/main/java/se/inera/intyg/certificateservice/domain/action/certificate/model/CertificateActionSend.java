package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;

@Builder
@Getter(AccessLevel.NONE)
public class CertificateActionSend implements CertificateAction {

  private static final String NAME_FK = "Skicka till Försäkringskassan";
  private static final String DESCRIPTION_FK = "Öppnar ett fönster där du kan välja att skicka intyget till Försäkringskassan.";
  private static final String BODY_FK = "<p>Om du går vidare kommer intyget skickas direkt till Försäkringskassans system vilket ska göras i samråd med patienten.</p>";
  private static final String NAME_TS = "Skicka till Transportstyrelsen";
  private static final String DESCRIPTION_TS = "Öppnar ett fönster där du kan välja att skicka intyget till Transportstyrelsen.";
  private static final String BODY_TS = "<p>Om du går vidare kommer intyget skickas direkt till Transportstyrelsens system vilket ska göras i samråd med patienten.</p>";
  private static final String TRANSPORTSTYRELSEN = "Transportstyrelsen";
  private static final String FORSAKRINGSKASSAN = "Försäkringskassan";
  private static final String UNSUPPORTED_RECIPIENT = "Unsupported recipient: %s";
  private static final String UNABLE_TO_RETRIEVE_RECIPIENT_FOR_CERTIFICATE = "Unable to retrieve recipient for certificate";
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
    return getRecipient(certificate)
        .map(CertificateActionSend::recipientName)
        .orElseThrow(
            () -> new IllegalStateException(UNABLE_TO_RETRIEVE_RECIPIENT_FOR_CERTIFICATE));
  }

  @Override
  public String getDescription(Optional<Certificate> certificate) {
    return getRecipient(certificate)
        .map(CertificateActionSend::recipientDescription)
        .orElseThrow(
            () -> new IllegalStateException(UNABLE_TO_RETRIEVE_RECIPIENT_FOR_CERTIFICATE));
  }

  @Override
  public String getBody(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return Optional.ofNullable(certificateActionSpecification.contentProvider())
        .map(contentProvider -> contentProvider.body(certificate.orElseThrow()))
        .orElseGet(
            () -> getRecipient(certificate)
                .map(CertificateActionSend::recipientBody)
                .orElseThrow(
                    () -> new IllegalStateException(UNABLE_TO_RETRIEVE_RECIPIENT_FOR_CERTIFICATE)
                )
        );
  }

  private static String recipientName(Recipient recipient) {
    return switch (recipient.name()) {
      case TRANSPORTSTYRELSEN -> NAME_TS;
      case FORSAKRINGSKASSAN -> NAME_FK;
      default -> throw new IllegalStateException(UNSUPPORTED_RECIPIENT.formatted(recipient.name()));
    };
  }

  private static String recipientBody(Recipient recipient) {
    return switch (recipient.name()) {
      case TRANSPORTSTYRELSEN -> BODY_TS;
      case FORSAKRINGSKASSAN -> BODY_FK;
      default -> throw new IllegalStateException(UNSUPPORTED_RECIPIENT.formatted(recipient.name()));
    };
  }

  private static String recipientDescription(Recipient recipient) {
    return switch (recipient.name()) {
      case TRANSPORTSTYRELSEN -> DESCRIPTION_TS;
      case FORSAKRINGSKASSAN -> DESCRIPTION_FK;
      default -> throw new IllegalStateException(UNSUPPORTED_RECIPIENT.formatted(recipient.name()));
    };
  }

  private static Optional<Recipient> getRecipient(Optional<Certificate> certificate) {
    return certificate
        .map(Certificate::certificateModel)
        .map(CertificateModel::recipient);
  }
}