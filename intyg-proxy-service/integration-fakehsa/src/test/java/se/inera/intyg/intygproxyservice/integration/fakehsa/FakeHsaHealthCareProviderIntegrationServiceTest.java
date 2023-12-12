package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaHealthCareProviderIntegrationServiceTest {

  @Mock
  private FakeHsaRepository fakeHsaRepository;

  @InjectMocks
  FakeHsaHealthCareProviderIntegrationService fakeHsaHealthCareProviderIntegrationService;

  @Test
  void shouldReturnResponseFromRepository() {
    final var expected = List.of(HealthCareProvider.builder().build());
    when(fakeHsaRepository.getHealthCareProvider(anyString()))
        .thenReturn(expected);

    final var response = fakeHsaHealthCareProviderIntegrationService.get(
        GetHealthCareProviderIntegrationRequest.builder().hsaId("HSA_ID").build()
    );

    assertEquals(expected, response.getHealthCareProviders());
  }

}