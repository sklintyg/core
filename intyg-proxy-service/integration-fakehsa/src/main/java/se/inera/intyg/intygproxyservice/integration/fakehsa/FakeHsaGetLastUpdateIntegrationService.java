package se.inera.intyg.intygproxyservice.integration.fakehsa;


import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationService;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@Service
@Profile(FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class FakeHsaGetLastUpdateIntegrationService implements GetLastUpdateIntegrationService {

  private final FakeHsaRepository fakeHsaRepository;

  @Override
  public GetLastUpdateIntegrationResponse get() {
    final var response = fakeHsaRepository.getLastUpdate();

    return GetLastUpdateIntegrationResponse.builder()
        .lastUpdate(response)
        .build();
  }
}
