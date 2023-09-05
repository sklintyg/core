package se.inera.intyg.intygproxyservice.integration.pu;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;

@Service
@Profile("!fakepu")
public class PuIntegrationService implements PuService {

  @Override
  public PuResponse findPerson(PuRequest puRequest) {
    return PuResponse.found(
        Person.builder()
            .personnummer(
                puRequest.getPersonId()
            )
            .build()
    );
  }
}
