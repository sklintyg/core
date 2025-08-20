package se.inera.intyg.intygproxyservice.citizen.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;

@Component
public class Elva77ResponseConverter {

  public CitizenResponse convert(Elva77Response elva77Response) {
    return null;
  }
}