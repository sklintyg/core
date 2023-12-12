package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationService;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@Service
@AllArgsConstructor
@Profile(FAKE_HSA_PROFILE)
public class FakeHsaHealthCareProviderIntegrationService implements
    GetHealthCareProviderIntegrationService {

  private final FakeHsaRepository fakeHsaRepository;

  @Override
  public GetHealthCareProviderIntegrationResponse get(
      GetHealthCareProviderIntegrationRequest request) {

    return GetHealthCareProviderIntegrationResponse.builder()
        .healthCareProviders(fakeHsaRepository.getHealthCareProvider(request.getHsaId()))
        .build();
  }
}
