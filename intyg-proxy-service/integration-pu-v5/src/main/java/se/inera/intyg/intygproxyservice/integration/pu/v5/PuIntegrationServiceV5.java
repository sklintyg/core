package se.inera.intyg.intygproxyservice.integration.pu.v5;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.pu.v5.client.PuClientV5;

@Service
@Slf4j
@RequiredArgsConstructor
public class PuIntegrationServiceV5 {

  private final PuClientV5 puClientV5;

  public PuResponse findPerson(PuRequest puRequest) {
    return puClientV5.findPerson(puRequest);
  }

  public PuPersonsResponse findPersons(PuPersonsRequest puRequest) {
    return puClientV5.findPersons(puRequest);
  }
}
