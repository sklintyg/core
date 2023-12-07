package se.inera.intyg.intygproxyservice.integration.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.authorization.client.HsaAuthorizationClient;

@ExtendWith(MockitoExtension.class)
class HsaGetLastUpdateIntegrationServiceTest {

  private static final LocalDateTime EXPECTED = LocalDateTime.now();

  @InjectMocks
  HsaGetLastUpdateIntegrationService hsaGetLastUpdateIntegrationService;

  @Mock
  HsaAuthorizationClient hsaAuthorizationClient;

  @Test
  void shouldReturnResponseFromClient() {
    when(hsaAuthorizationClient.getLastUpdate())
        .thenReturn(EXPECTED);

    final var response = hsaGetLastUpdateIntegrationService.get();

    assertEquals(EXPECTED, response.getLastUpdate());
  }

}