package se.inera.intyg.intygproxyservice.integration.fakepu;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.FAKE_PU_PROFILE;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.FakePuRepository;

@Service
@AllArgsConstructor
@Profile(FAKE_PU_PROFILE)
public class FakePuIntegrationService implements PuService {

  private final FakePuRepository fakePuRepository;

  @Override
  public PuResponse findPerson(PuRequest puRequest) {
    if (puRequest == null) {
      throw new IllegalArgumentException("Cannot be null!");
    }

    final var person = fakePuRepository.getPerson(puRequest.getPersonId());
    if (person == null) {
      return PuResponse.notFound();
    }

    return PuResponse.found(person);
  }
}
