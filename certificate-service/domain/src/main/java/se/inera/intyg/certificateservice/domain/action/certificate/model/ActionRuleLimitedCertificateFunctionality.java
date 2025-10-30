package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;

@RequiredArgsConstructor
public class ActionRuleLimitedCertificateFunctionality implements ActionRule {

  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  private final CertificateActionType certificateActionType;

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    if (certificate.isEmpty()) {
      return false;
    }
    
    final var evaluatedCertificate = certificate.get();
    if (evaluatedCertificate.certificateModel().isLastestActiveVersion()) {
      return true;
    }

    final var limitedCertificateFunctionalityConfiguration = certificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
        evaluatedCertificate.certificateModel().id()
    );

    if (limitedCertificateFunctionalityConfiguration == null) {
      return true;
    }

    return limitedCertificateFunctionalityConfiguration.configuration().actions().stream()
        .filter(limitedActionConfiguration ->
            limitedActionConfiguration.type().equals(certificateActionType.name())
        )
        .map(config -> LocalDateTime.now().isBefore(config.untilDateTime()))
        .findFirst()
        .orElse(false);
  }
}