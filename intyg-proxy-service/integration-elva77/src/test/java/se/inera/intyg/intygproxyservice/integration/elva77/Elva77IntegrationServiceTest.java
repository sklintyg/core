package se.inera.intyg.intygproxyservice.integration.elva77;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.elva77.client.Elva77Client;

@ExtendWith(MockitoExtension.class)
class Elva77IntegrationServiceTest {

  @Mock
  Elva77Client elva77Client;
  @InjectMocks
  Elva77IntegrationService elva77IntegrationService;

  @Test
  void shouldReturnElva77Response() {
    final var expectedResponse = Elva77Response.builder().build();
    final var request = Elva77Request.builder().build();

    when(elva77Client.getUserProfile(request)).thenReturn(expectedResponse);

    final var response = elva77IntegrationService.findCitizen(request);
    assertEquals(expectedResponse, response);
  }
}