package se.inera.intyg.intygproxyservice.user.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.elva77.User;
import se.inera.intyg.intygproxyservice.user.dto.UserDTO;

@Component
public class Elva77ResponseConverter {

  public UserDTO convert(User user) {
    return null;
  }
}