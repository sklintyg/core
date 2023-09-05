package se.inera.intyg.intygproxyservice.person;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse.Status;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;

@Service
@AllArgsConstructor
public class PersonService {

  private final PuService puService;

  public PersonResponse findPerson(PersonRequest personRequest) {
    validateRequest(personRequest);

    final var puResponse = findPersonInPu(personRequest);
    return convert(puResponse);
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

  private static PersonResponse convert(PuResponse puResponse) {
    return PersonResponse.builder()
        .person(
            Status.FOUND.equals(puResponse.getStatus()) ? puResponse.getPerson() : null
        )
        .status(
            puResponse.getStatus()
        )
        .build();
  }
}
