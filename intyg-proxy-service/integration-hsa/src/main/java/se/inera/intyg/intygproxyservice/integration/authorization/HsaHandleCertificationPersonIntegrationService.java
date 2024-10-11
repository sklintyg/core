package se.inera.intyg.intygproxyservice.integration.authorization;


import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;
import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;
import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_CHANGE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.authorization.client.HsaAuthorizationClient;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;

@Service
@Profile("!" + FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class HsaHandleCertificationPersonIntegrationService implements
    HandleCertificationPersonIntegrationService {

  private final HsaAuthorizationClient hsaAuthorizationClient;

  @Override
  @PerformanceLogging(eventAction = "handle-certification-person", eventType = EVENT_TYPE_CHANGE)
  public HandleCertificationPersonIntegrationResponse get(
      HandleCertificationPersonIntegrationRequest request) {
    final var response = hsaAuthorizationClient.handleCertificationPerson(request);

    return HandleCertificationPersonIntegrationResponse.builder()
        .result(response)
        .build();
  }
}
