package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaGetLastUpdateIntegrationServiceTest {

  @Mock
  private FakeHsaRepository fakeHsaRepository;

  @InjectMocks
  private FakeHsaGetLastUpdateIntegrationService getLastUpdateIntegrationService;

  @Test
  void shouldReturnResponseWithLastUpdate() {
    final var localDateTime = LocalDateTime.now();
    final var expectedResponse = GetLastUpdateIntegrationResponse.builder()
        .lastUpdate(localDateTime)
        .build();

    when(fakeHsaRepository.getLastUpdate()).thenReturn(localDateTime);

    final var result = getLastUpdateIntegrationService.get();

    assertEquals(expectedResponse, result);
  }
}
