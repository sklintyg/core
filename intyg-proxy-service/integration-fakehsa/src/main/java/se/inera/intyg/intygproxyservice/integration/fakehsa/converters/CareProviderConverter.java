package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;

@Component
public class CareProviderConverter {

  public HealthCareProvider convert(ParsedCareProvider parsedCareProvider) {
    return HealthCareProvider.builder()
        .healthCareProviderName(parsedCareProvider.getName())
        .healthCareProviderHsaId(parsedCareProvider.getId())
        .build();
  }
}
