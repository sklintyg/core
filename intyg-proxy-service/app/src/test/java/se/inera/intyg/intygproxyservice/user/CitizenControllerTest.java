package se.inera.intyg.intygproxyservice.citizen;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenDTO;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenRequest;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.citizen.service.CitizenService;

@ExtendWith(MockitoExtension.class)
class CitizenControllerTest {

  private static final CitizenRequest REQUEST = CitizenRequest.builder().build();

  @Mock
  CitizenService citizenService;
  @InjectMocks
  CitizenController citizenController;

  @Test
  void shouldFindCitizen() {
    final var expectedResponse = CitizenResponse.builder()
        .citizen(CitizenDTO.builder().build())
        .build();

    when(citizenService.findCitizen(REQUEST)).thenReturn(expectedResponse);

    final var response = citizenController.findUser(REQUEST);
    assertEquals(expectedResponse, response);
  }
}