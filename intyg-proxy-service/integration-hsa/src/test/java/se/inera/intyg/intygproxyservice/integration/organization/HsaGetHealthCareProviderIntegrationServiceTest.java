package se.inera.intyg.intygproxyservice.integration.organization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.integration.organization.client.HsaOrganizationClient;

@ExtendWith(MockitoExtension.class)
class HsaGetHealthCareProviderIntegrationServiceTest {

  @Mock
  private HsaOrganizationClient hsaOrganizationClient;

  @InjectMocks
  private HsaGetHealthCareProviderIntegrationService getHealthCareProviderIntegrationService;

  @Test
  void shouldReturnResponseFromClient() {
    final var expected = List.of(HealthCareProvider.builder().build());
    when(hsaOrganizationClient.getHealthCareProvider(
        any(GetHealthCareProviderIntegrationRequest.class)))
        .thenReturn(expected);

    final var response = getHealthCareProviderIntegrationService.get(
        GetHealthCareProviderIntegrationRequest.builder().build()
    );

    assertEquals(expected, response.getHealthCareProviders());
  }

}