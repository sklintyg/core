package se.inera.intyg.intygproxyservice.organization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareProviderRequest;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareProviderResponse;
import se.inera.intyg.intygproxyservice.organization.service.HealthCareProviderService;

@ExtendWith(MockitoExtension.class)
class HealthCareProviderControllerTest {

  @Mock
  private HealthCareProviderService healthCareProviderService;

  @InjectMocks
  private HealthCareProviderController healthCareProviderController;

  @Test
  void shouldReturnResponseFromService() {
    final var expected = HealthCareProviderResponse.builder()
        .healthCareProviders(List.of(HealthCareProvider.builder().build()))
        .build();
    when(healthCareProviderService.get(any(HealthCareProviderRequest.class)))
        .thenReturn(expected);

    final var response = healthCareProviderController.getHealthCareProvider(
        HealthCareProviderRequest
            .builder()
            .build()
    );

    assertEquals(expected, response);
  }
}