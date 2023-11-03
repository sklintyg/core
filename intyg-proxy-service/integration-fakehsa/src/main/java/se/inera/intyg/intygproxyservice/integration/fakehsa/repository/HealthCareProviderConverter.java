package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;


import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;

@Component
public class HealthCareProviderConverter {

  public HealthCareProvider convert(ParsedCareProvider parsedCareProvider) {
    return HealthCareProvider.builder()
        .healthCareProviderHsaId(parsedCareProvider.getId())
        .healthCareProviderName(parsedCareProvider.getName())
        .build();
  }
}
