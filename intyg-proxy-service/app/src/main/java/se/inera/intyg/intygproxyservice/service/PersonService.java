package se.inera.intyg.intygproxyservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.dto.PersonResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;

@Service
@AllArgsConstructor
public class PersonService {

  private final PuService puService;

  public PersonResponse findPerson(PersonRequest personRequest) {
    final var puResponse = puService.findPerson(
        PuRequest.builder()
            .personId(
                personRequest.getId()
            )
            .build()
    );

    return PersonResponse.builder()
        .id(
            puResponse.getPerson().getPersonnummer()
        )
        .build();
  }
}
