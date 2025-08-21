package se.inera.intyg.intygproxyservice.citizen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenRequest;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Service;

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

    return elva77ResponseConverter.convert(elva77Response);
  }

  private void validateRequest(CitizenRequest citizenRequest) {
    if (citizenRequest == null) {
      throw new IllegalArgumentException("Invalid request, CitizenRequest is null");
    }

    if (citizenRequest.getPersonId() == null || citizenRequest.getPersonId().isBlank()) {
      throw new IllegalArgumentException("Invalid request, PersonId is missing");
    }
  }
}