package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

@Builder
@Getter(AccessLevel.NONE)
public class CertificateActionPrint implements CertificateAction {

  private static final String NAME = "Skriv ut";
  private static final String DESCRIPTION = "Öppnar ett fönster där du kan välja att skriva ut eller spara intyget som PDF.";
  private static final String DRAFT_DESCRIPTION = "Öppnar ett fönster där du kan välja att skriva ut eller spara intygsutkastet som PDF.";
  private final CertificateActionSpecification certificateActionSpecification;
  private final List<ActionRule> actionRules;
  private static final String PRINT_PROTECTED_PERSON_BODY =
      "<div class='ic-alert ic-alert--status ic-alert--info'>\n"
          + "<i class='ic-alert__icon ic-info-icon'></i><p>Patienten har skyddade personuppgifter. Hantera utskriften varsamt.</p></div>";

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
    if (certificate.map(Certificate::isDraft).orElse(false)) {
      return DRAFT_DESCRIPTION;
    }

    return DESCRIPTION;
  }

  @Override
  public String getBody(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    final var patient = certificate
        .map(Certificate::certificateMetaData)
        .map(CertificateMetaData::patient)
        .orElse(null);

    if (patient == null) {
      return null;
    }

    return patient.protectedPerson().value() ? PRINT_PROTECTED_PERSON_BODY : null;
  }
}