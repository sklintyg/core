package se.inera.intyg.intygproxyservice.person.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonResponse;

@Service
@RequiredArgsConstructor
public class PersonService {

  private final PuService puService;
  private final PersonDTOMapper personDTOMapper;

  public PersonResponse findPerson(PersonRequest personRequest) {
    validateRequest(personRequest);

    final var puResponse = findPersonInPu(personRequest);
    return PuResponseConverter.convert(personDTOMapper, puResponse);
  }

  private static void validateRequest(PersonRequest personRequest) {
    if (personRequest == null) {
      throw new IllegalArgumentException("PersonRequest is null");
    }

    if (personRequest.getPersonId() == null || personRequest.getPersonId().isBlank()) {
      throw new IllegalArgumentException(
          String.format("PersonId is not valid: '%s'", personRequest.getPersonId())
      );
    }
  }

  private PuResponse findPersonInPu(PersonRequest personRequest) {
    return puService.findPerson(
        PuRequest.builder()
            .personId(
                personRequest.getPersonId()
            )
            .build()
    );
  }
}
