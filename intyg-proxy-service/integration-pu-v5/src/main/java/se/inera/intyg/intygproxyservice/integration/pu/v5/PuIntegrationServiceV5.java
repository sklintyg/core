package se.inera.intyg.intygproxyservice.integration.pu.v5;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V5;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.pu.v5.client.PuClientV5;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile(PU_PROFILE_V5)
public class PuIntegrationServiceV5 implements PuService {

  private final PuClientV5 puClientV5;

  @Override
  public PuResponse findPerson(PuRequest puRequest) {
    return puClientV5.findPerson(puRequest);
  }
}
