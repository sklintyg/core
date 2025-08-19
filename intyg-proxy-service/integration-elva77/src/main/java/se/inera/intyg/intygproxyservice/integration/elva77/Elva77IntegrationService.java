package se.inera.intyg.intygproxyservice.integration.elva77;

import static se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Constants.ELVA77_PROFILE_ACTIVE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Service;
import se.inera.intyg.intygproxyservice.integration.elva77.client.Elva77Client;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile(ELVA77_PROFILE_ACTIVE)
public class Elva77IntegrationService implements Elva77Service {

  private final Elva77Client elva77Client;

  @Override
  public Elva77Response findUser(Elva77Request request) {
    return elva77Client.findUser(request);
  }
}