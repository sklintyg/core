package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.CertificateInactiveConfiguration;

@RequiredArgsConstructor
public class ActionRuleInactiveCertificateType implements ActionRule {

  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    final var inactiveConfiguration = certificateActionConfigurationRepository.findInactiveConfiguration(
        certificate.orElseThrow().certificateModel().id()
    );

    if (inactiveConfiguration.isEmpty()) {
      return true;
    }
    
    return inactiveConfiguration.stream()
        .map(CertificateInactiveConfiguration::configuration)
        .noneMatch(configuration -> configuration.fromDateTime().isBefore(LocalDateTime.now()));
  }
}