package se.inera.intyg.intygproxyservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Service;
import se.inera.intyg.intygproxyservice.user.dto.CitizenRequest;
import se.inera.intyg.intygproxyservice.user.dto.CitizenResponse;

@Service
@RequiredArgsConstructor
public class CitizenService {

  private final Elva77Service elva77Service;
  private final Elva77ResponseConverter elva77ResponseConverter;

  public CitizenResponse findCitizen(CitizenRequest citizenRequest) {
    validateRequest(citizenRequest);

    final var elva77Response = elva77Service.findCitizen(
        Elva77Request.builder()
            .personId(citizenRequest.getPersonId())
            .build()
    );

    return CitizenResponse.builder()
        .citizen(elva77ResponseConverter.convert(elva77Response.getCitizen()))
        .build();
  }

  private void validateRequest(CitizenRequest citizenRequest) {
    if (citizenRequest == null) {
      throw new IllegalArgumentException("Invalid request, UserRequest is null");
    }

    if (citizenRequest.getPersonId() == null || citizenRequest.getPersonId().isBlank()) {
      throw new IllegalArgumentException("Invalid request, PersonId is missing");
    }
  }
}