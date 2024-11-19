package se.inera.intyg.certificateservice.infrastructure.unitaccess;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.infrastructure.configuration.UnitAccessConfiguration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InMemoryCertificateAccessConfigurationRepository implements
    CertificateActionConfigurationRepository {

  private final UnitAccessConfiguration unitAccessConfiguration;

  @Override
  public List<CertificateAccessConfiguration> find(CertificateType certificateType) {
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
}
