package se.inera.intyg.intygproxyservice.integration.fakepu;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.FAKE_PU_PROFILE;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
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

    return getPersonFromFakeRepository(puRequest.getPersonId());
  }

  @Override
  public PuPersonsResponse findPersons(PuPersonsRequest puRequest) {
    if (puRequest == null) {
      throw new IllegalArgumentException("Cannot be null!");
    }

    final var persons = puRequest.getPersonIds()
        .stream()
        .map(this::getPersonFromFakeRepository)
        .toList();

    return PuPersonsResponse.builder()
        .persons(persons)
        .build();
  }

  private PuResponse getPersonFromFakeRepository(String personId) {
    final var person = fakePuRepository.getPerson(personId);
    if (person == null) {
      return PuResponse.notFound();
    }

    return PuResponse.found(person);
  }
}
