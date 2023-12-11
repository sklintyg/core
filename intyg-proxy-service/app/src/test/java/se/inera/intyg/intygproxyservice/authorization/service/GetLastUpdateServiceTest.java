package se.inera.intyg.intygproxyservice.authorization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationService;

@ExtendWith(MockitoExtension.class)
class GetLastUpdateServiceTest {

  private static final LocalDateTime EXPECTED = LocalDateTime.now();

  @Mock
  GetLastUpdateIntegrationService getLastUpdateIntegrationService;

  @InjectMocks
  GetLastUpdateService getLastUpdateService;

  @Test
  void shouldReturnValueFromIntegrationService() {
    when(getLastUpdateIntegrationService.get())
        .thenReturn(
            GetLastUpdateIntegrationResponse.builder()
                .lastUpdate(EXPECTED)
                .build()
        );

    final var response = getLastUpdateService.get();

    assertEquals(EXPECTED, response.getLastUpdate());
  }

  @Test
  void shouldReturnNullIfIntegrationServiceReturnsNull() {
    final var response = getLastUpdateService.get();

    assertNull(response.getLastUpdate());
  }
}