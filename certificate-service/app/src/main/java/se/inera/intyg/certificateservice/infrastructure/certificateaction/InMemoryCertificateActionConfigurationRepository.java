package se.inera.intyg.certificateservice.infrastructure.certificateaction;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.infrastructure.configuration.GetLimitedCertificateFunctionalityConfiguration;
import se.inera.intyg.certificateservice.infrastructure.configuration.UnitAccessConfiguration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InMemoryCertificateActionConfigurationRepository implements
    CertificateActionConfigurationRepository {

  private final UnitAccessConfiguration unitAccessConfiguration;
  private final GetLimitedCertificateFunctionalityConfiguration getLimitedCertificateFunctionalityConfiguration;

  @Override
  public List<CertificateAccessConfiguration> findAccessConfiguration(
      CertificateType certificateType) {
    final var certificateAccessConfigurations = unitAccessConfiguration.get();

    if (certificateAccessConfigurations.isEmpty()) {
      return Collections.emptyList();
    }

    return certificateAccessConfigurations.stream()
        .filter(
            configuration -> configuration.certificateType().equals(certificateType.type())
        )
        .toList();
  }

  @Override
  public LimitedCertificateFunctionalityConfiguration findLimitedCertificateFunctionalityConfiguration(
      CertificateModelId certificateModelId) {
    final var limitedCertificateFunctionalityConfigurations = getLimitedCertificateFunctionalityConfiguration.get();
    if (limitedCertificateFunctionalityConfigurations.isEmpty()) {
      return null;
    }

    return limitedCertificateFunctionalityConfigurations.stream()
        .filter(
            config -> certificateModelId.matches(config.certificateType(), config.version())
        )
        .findFirst()
        .orElse(null);
  }
}