package se.inera.intyg.intygproxyservice.person.service;

import static se.inera.intyg.intygproxyservice.integration.api.pu.Status.FOUND;

import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
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
            FOUND.equals(puResponse.getStatus())
                ? mapper.toDTO(puResponse.getPerson())
                : PersonDTO.builder().personnummer(puResponse.getPerson().getPersonnummer()).build()
        )
        .status(
            StatusDTOType.valueOf(puResponse.getStatus().name())
        )
        .build();
  }
}
