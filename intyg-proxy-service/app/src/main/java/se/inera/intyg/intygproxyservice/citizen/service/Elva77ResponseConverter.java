package se.inera.intyg.intygproxyservice.citizen.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenDTO;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;

@Component
public class Elva77ResponseConverter {

  public CitizenResponse convert(Elva77Response elva77Response) {
    return switch (elva77Response.getResult()) {
      case ERROR -> CitizenResponse.builder().status(Status.ERROR).build();
      case OK, INFO -> {
        final var citizen = elva77Response.getCitizen();
        yield CitizenResponse.builder()
            .citizen(
                CitizenDTO.builder()
                    .personnummer(citizen.getSubjectOfCareId())
                    .fornamn(citizen.getFirstname())
                    .efternamn(citizen.getLastname())
                    .postort(citizen.getCity())
                    .postadress(citizen.getStreetAddress())
                    .postnummer(citizen.getZip())
                    .isActive(citizen.getIsActive())
                    .build()
            )
            .status(Status.FOUND)
            .build();
      }
    };
  }
}