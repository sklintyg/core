package se.inera.intyg.intygproxyservice.integration.authorization;


import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationService;
import se.inera.intyg.intygproxyservice.integration.authorization.client.HsaAuthorizationClient;

@Service
@Profile("!" + FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class HsaGetCredentialInformationIntegrationService implements
    GetCredentialInformationIntegrationService {

  private final HsaAuthorizationClient hsaAuthorizationClient;

  @Override
  public GetCredentialInformationIntegrationResponse get(
      GetCredentialInformationIntegrationRequest request) {
    final var response = hsaAuthorizationClient.getCredentialInformation(request);

    return GetCredentialInformationIntegrationResponse.builder()
        .credentialInformation(response)
        .build();
  }
}
