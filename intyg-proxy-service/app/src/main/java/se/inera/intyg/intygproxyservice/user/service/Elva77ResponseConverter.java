package se.inera.intyg.intygproxyservice.user.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Citizen;
import se.inera.intyg.intygproxyservice.user.dto.CitizenDTO;

@Component
public class Elva77ResponseConverter {

  public CitizenDTO convert(Citizen citizen) {
    return null;
  }
}