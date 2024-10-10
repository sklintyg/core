package se.inera.intyg.intygproxyservice.integration.authorization;


import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;
import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationService;
import se.inera.intyg.intygproxyservice.integration.authorization.client.HsaAuthorizationClient;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;

@Service
@Profile("!" + FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class HsaGetLastUpdateIntegrationService implements GetLastUpdateIntegrationService {

  private final HsaAuthorizationClient hsaAuthorizationClient;

  @Override
  @PerformanceLogging(eventAction = "get-last-update", eventType = EVENT_TYPE_ACCESSED)
  public GetLastUpdateIntegrationResponse get() {
    final var response = hsaAuthorizationClient.getLastUpdate();

    return GetLastUpdateIntegrationResponse.builder()
        .lastUpdate(response)
        .build();
  }
}
