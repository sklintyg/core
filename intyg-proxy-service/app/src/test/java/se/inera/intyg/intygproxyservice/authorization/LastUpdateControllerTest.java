package se.inera.intyg.intygproxyservice.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.authorization.dto.GetLastUpdateResponse;
import se.inera.intyg.intygproxyservice.authorization.service.GetLastUpdateService;

@ExtendWith(MockitoExtension.class)
class LastUpdateControllerTest {

  private static final GetLastUpdateResponse EXPECTED =
      GetLastUpdateResponse.builder()
          .lastUpdate(LocalDateTime.now())
          .build();

  @Mock
  GetLastUpdateService getLastUpdateService;

  @InjectMocks
  LastUpdateController lastUpdateController;

  @Test
  void shouldReturnValueFromService() {
    when(getLastUpdateService.get())
        .thenReturn(EXPECTED);

    final var response = lastUpdateController.getLastUpdate();

    assertEquals(EXPECTED, response);
  }

}