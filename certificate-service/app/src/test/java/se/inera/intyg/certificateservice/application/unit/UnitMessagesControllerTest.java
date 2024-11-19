package se.inera.intyg.certificateservice.application.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesResponse;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitMessagesService;

@ExtendWith(MockitoExtension.class)
class UnitMessagesControllerTest {

  @Mock
  GetUnitMessagesService getUnitMessagesService;

  @InjectMocks
  UnitMessagesController unitMessagesController;

  @Test
  void shouldReturnResponseFromGetUnitMessagesService() {
    final var request = GetUnitMessagesRequest.builder().build();
    final var expectedResponse = GetUnitMessagesResponse.builder().build();
    when(getUnitMessagesService.get(request))
        .thenReturn(expectedResponse);

    final var response = unitMessagesController.getUnitMessages(request);

    assertEquals(expectedResponse, response);
  }
}