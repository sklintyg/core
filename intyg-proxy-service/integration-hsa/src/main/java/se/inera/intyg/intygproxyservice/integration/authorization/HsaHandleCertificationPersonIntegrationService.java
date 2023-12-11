package se.inera.intyg.intygproxyservice.integration.authorization;


import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.authorization.client.HsaAuthorizationClient;

@Service
@Profile("!" + FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class HsaHandleCertificationPersonIntegrationService implements
    HandleCertificationPersonIntegrationService {

  private final HsaAuthorizationClient hsaAuthorizationClient;

  @Override
  public HandleCertificationPersonIntegrationResponse get(
      HandleCertificationPersonIntegrationRequest request) {
    final var response = hsaAuthorizationClient.handleCertificationPerson(request);

    return HandleCertificationPersonIntegrationResponse.builder()
        .result(response)
        .build();
  }
}
