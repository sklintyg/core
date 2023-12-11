package se.inera.intyg.intygproxyservice.integration.authorization;


import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.authorization.client.HsaAuthorizationClient;

@Service
@Profile("!" + FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class HsaGetCredentialsForPersonIntegrationService implements
    GetCredentialsForPersonIntegrationService {

  private final HsaAuthorizationClient hsaAuthorizationClient;

  @Override
  public GetCredentialsForPersonIntegrationResponse get(
      GetCredentialsForPersonIntegrationRequest request) {
    final var response = hsaAuthorizationClient.getCredentialsForPerson(request);

    return GetCredentialsForPersonIntegrationResponse.builder()
        .credentials(response)
        .build();
  }
}
