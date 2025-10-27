package se.inera.intyg.certificateservice.infrastructure.unitaccess;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.CertificateInactiveConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.infrastructure.configuration.GetInactiveCertificateConfiguration;
import se.inera.intyg.certificateservice.infrastructure.configuration.UnitAccessConfiguration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InMemoryCertificateActionConfigurationRepository implements
    CertificateActionConfigurationRepository {

  private final UnitAccessConfiguration unitAccessConfiguration;
  private final GetInactiveCertificateConfiguration inactiveCertificateConfiguration;

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
  public List<CertificateInactiveConfiguration> findInactiveConfiguration(
      CertificateModelId certificateModelId) {
    final var inactiveCertificateConfigurations = inactiveCertificateConfiguration.get();

    if (inactiveCertificateConfigurations.isEmpty()) {
      return Collections.emptyList();
    }

    return inactiveCertificateConfigurations.stream()
        .filter(
            configuration ->
                configuration.certificateType().equals(certificateModelId.type().type())
                    && configuration.version().equals(certificateModelId.version().version())
        )
        .toList();
  }
}