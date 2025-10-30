package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessUnitConfiguration;

@RequiredArgsConstructor
public class ActionRuleCertificateTypeActiveForUnit implements ActionRule {

  private static final String ALLOW = "allow";
  private static final LocalDateTime NOW = LocalDateTime.now();
  private static final String BLOCK = "block";
  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    if (certificate.isEmpty() || actionEvaluation.isEmpty()) {
      return false;
    }

    return evaluate(certificate.get().certificateModel().id().type(), actionEvaluation.get());
  }

  public boolean evaluate(CertificateType certificateType, ActionEvaluation actionEvaluation) {
    final var accessConfigurationForType = certificateActionConfigurationRepository.findAccessConfiguration(
        certificateType
    );

    if (certificateTypeHasNoConfiguration(accessConfigurationForType)) {
      return true;
    }

    final var activeAllowConfiguration = accessConfigurationForType.stream()
        .flatMap(hasActiveType(ALLOW))
        .toList();

    final var activeBlockConfiguration = accessConfigurationForType.stream()
        .flatMap(hasActiveType(BLOCK))
        .toList();

    if (noActiveConfiguration(activeBlockConfiguration, activeAllowConfiguration)) {
      return true;
    }

    if (activeBlockConfiguration.stream()
        .anyMatch(config -> containsUnit(config, actionEvaluation))) {
      return false;
    }

    return activeAllowConfiguration.isEmpty() || activeAllowConfiguration.stream()
        .anyMatch(config -> containsUnit(config, actionEvaluation));
  }

  private static Function<CertificateAccessConfiguration, Stream<CertificateAccessUnitConfiguration>> hasActiveType(
      String type) {
    return accessConfiguration -> accessConfiguration.configuration().stream()
        .filter(configuration -> configuration.type().equals(type))
        .filter(configuration -> configurationIsActive().apply(configuration));
  }

  private static boolean noActiveConfiguration(
      List<CertificateAccessUnitConfiguration> activeBlockConfiguration,
      List<CertificateAccessUnitConfiguration> activeAllowConfiguration) {
    return activeBlockConfiguration.isEmpty() && activeAllowConfiguration.isEmpty();
  }

  private static Function<CertificateAccessUnitConfiguration, Boolean> configurationIsActive() {
    return configuration -> (configuration.fromDateTime() != null
        && configuration.fromDateTime().isBefore(NOW))
        && (configuration.toDateTime() == null
        || configuration.toDateTime().isAfter(NOW));
  }

  private static boolean certificateTypeHasNoConfiguration(
      List<CertificateAccessConfiguration> accessConfigurationForType) {
    return accessConfigurationForType.isEmpty();
  }

  private static boolean containsUnit(
      CertificateAccessUnitConfiguration accessConfiguration, ActionEvaluation actionEvaluation) {
    final var subUnitId = actionEvaluation.subUnit().hsaId().id();
    final var careUnitId = actionEvaluation.careUnit().hsaId().id();
    final var careProviderId = actionEvaluation.careProvider().hsaId().id();

    return accessConfiguration.issuedOnUnit().contains(subUnitId)
        || accessConfiguration.careUnit().contains(careUnitId)
        || accessConfiguration.careProviders().contains(careProviderId);
  }
}