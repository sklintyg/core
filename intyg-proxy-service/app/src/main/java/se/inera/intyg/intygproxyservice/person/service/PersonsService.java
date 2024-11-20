package se.inera.intyg.intygproxyservice.person.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.person.dto.PersonsRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonsResponse;

@Service
@RequiredArgsConstructor
public class PersonsService {

  private final PuService puService;
  private final PersonDTOMapper personDTOMapper;

  public PersonsResponse findPersons(PersonsRequest request) {
    validateRequest(request);

    final var puResponse = findPersonsInPu(request);
    return convert(puResponse);
  }

  private static void validateRequest(PersonsRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("PersonsRequest is null");
    }
  }

  private PuPersonsResponse findPersonsInPu(PersonsRequest personRequest) {
    return puService.findPersons(
        PuPersonsRequest.builder()
            .personIds(
                personRequest.getPersonIds()
            )
            .build()
    );
  }

  private PersonsResponse convert(PuPersonsResponse puResponse) {
    return PersonsResponse.builder()
        .persons(
            puResponse.getPersons()
                .stream()
                .map(response -> PuResponseConverter.convert(personDTOMapper, response))
                .toList()
        )
        .build();
  }
}
