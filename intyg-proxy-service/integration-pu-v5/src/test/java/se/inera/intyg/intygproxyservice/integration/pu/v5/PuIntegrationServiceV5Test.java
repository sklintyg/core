package se.inera.intyg.intygproxyservice.integration.pu.v5;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.pu.v5.client.PuClientV5;

@ExtendWith(MockitoExtension.class)
class PuIntegrationServiceV5Test {

  @Mock
  PuClientV5 puClient;

  @InjectMocks
  private PuIntegrationServiceV5 puIntegrationServiceV5;

  @Test
  void shouldCallClientForGetPerson() {
    final var request = PuRequest.builder().build();
    puIntegrationServiceV5.findPerson(request);

    verify(puClient).findPerson(request);
  }

  @Test
  void shouldCallClientForGetPersons() {
    final var request = PuPersonsRequest.builder().build();
    puIntegrationServiceV5.findPersons(request);

    verify(puClient).findPersons(request);
  }

}