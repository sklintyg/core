package se.inera.intyg.intygproxyservice.integration.organization;

import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationService;
import se.inera.intyg.intygproxyservice.integration.organization.client.HsaOrganizationClient;

@Service
@Profile("!" + FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class HsaGetHealthCareProviderIntegrationService implements
    GetHealthCareProviderIntegrationService {

  private final HsaOrganizationClient hsaOrganizationClient;

  @Override
  public GetHealthCareProviderIntegrationResponse get(
      GetHealthCareProviderIntegrationRequest request) {
    final var response = hsaOrganizationClient.getHealthCareProvider(request);

    return GetHealthCareProviderIntegrationResponse.builder()
        .healthCareProviders(response)
        .build();
  }
}
