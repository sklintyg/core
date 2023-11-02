package se.inera.intyg.intygproxyservice.integration.pu;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.FAKE_PU_PROFILE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.pu.client.PuClient;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("!" + FAKE_PU_PROFILE)
public class PuIntegrationService implements PuService {

  private final PuClient puClient;

  @Override
  public PuResponse findPerson(PuRequest puRequest) {
    return puClient.findPerson(puRequest);
  }
}
