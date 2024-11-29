package se.inera.intyg.intygproxyservice.person.service;

import static se.inera.intyg.intygproxyservice.integration.api.pu.Status.FOUND;

import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;
import se.inera.intyg.intygproxyservice.person.dto.PersonResponse;
import se.inera.intyg.intygproxyservice.person.dto.StatusDTOType;

public class PuResponseConverter {

  private PuResponseConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static PersonResponse convert(PersonDTOMapper mapper, PuResponse puResponse) {
    return PersonResponse.builder()
        .person(
            getPerson(puResponse.person(), puResponse.status(), mapper)
        )
        .status(
            StatusDTOType.valueOf(puResponse.status().name())
        )
        .build();
  }

  private static PersonDTO getPerson(Person person, Status status, PersonDTOMapper mapper) {
    if (person == null) {
      return null;
    }

    return FOUND.equals(status)
        ? mapper.toDTO(person)
        : PersonDTO.builder().personnummer(person.getPersonnummer()).build();
  }
}