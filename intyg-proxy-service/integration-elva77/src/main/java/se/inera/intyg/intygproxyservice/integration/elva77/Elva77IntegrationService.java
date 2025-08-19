package se.inera.intyg.intygproxyservice.integration.elva77;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Elva77IntegrationService implements Elva77Service {

  @Override
  public Elva77Response findUser(Elva77Request request) {
    return null;
  }
}