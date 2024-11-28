package se.inera.intyg.intygproxyservice.integration.pu;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.pu.client.PuClientV3;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile(PU_PROFILE_V3)
public class PuIntegrationServiceV3 implements PuService {

  private final PuClientV3 puClientV3;

  @Override
  public PuResponse findPerson(PuRequest puRequest) {
    return puClientV3.findPerson(puRequest);
  }

  @Override
  public PuPersonsResponse findPersons(PuPersonsRequest puRequest) {
    throw new IllegalStateException(
        "Find persons is not implemented for v3, use a higher PU version.");
  }
}
