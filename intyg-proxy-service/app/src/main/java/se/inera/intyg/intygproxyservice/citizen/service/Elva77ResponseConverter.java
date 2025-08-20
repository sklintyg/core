package se.inera.intyg.intygproxyservice.citizen.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenDTO;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Citizen;

@Component
public class Elva77ResponseConverter {

  public CitizenDTO convert(Citizen citizen) {
    return null;
  }
}