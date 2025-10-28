package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;

@RequiredArgsConstructor
public class ActionRuleLimitedFunctionality implements ActionRule {

  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  private final CertificateActionType certificateActionType;

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    final var evaluatedCertificate = certificate.orElseThrow(() -> new IllegalStateException(
        "Certificate is required for evaluating ActionRuleInactiveCertificateType"));

    if (isLastestActiveVersion(evaluatedCertificate)) {
      return true;
    }

    final var limitedFunctionalityConfiguration = certificateActionConfigurationRepository.findLimitedFunctionalityConfiguration(
        evaluatedCertificate.certificateModel().id()
    );

    if (limitedFunctionalityConfiguration == null) {
      return true;
    }

    return limitedFunctionalityConfiguration.configuration().actions().stream()
        .filter(limitedActionConfiguration ->
            limitedActionConfiguration.type().equals(certificateActionType.name())
        )
        .map(config -> LocalDateTime.now().isBefore(config.untilDateTime()))
        .findFirst()
        .orElse(false);
  }

  private static boolean isLastestActiveVersion(Certificate evaluatedCertificate) {
    return evaluatedCertificate.certificateModel().id().version()
        .isLastestActiveVersion(evaluatedCertificate.certificateModel().certificateVersions());
  }
}