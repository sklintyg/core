package se.inera.intyg.intygproxyservice.integration.pu.v4;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.pu.v4.client.PuClientV4;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile(PU_PROFILE_V4)
public class PuIntegrationServiceV4 implements PuService {

  private final PuClientV4 puClientV4;

  @Override
  public PuResponse findPerson(PuRequest puRequest) {
    return puClientV4.findPerson(puRequest);
  }
}
