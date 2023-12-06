package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaGetUnitIntegrationServiceTest {

  private static final String HSA_ID = "hsaId";
  @Mock
  private FakeHsaRepository fakeHsaRepository;

  @InjectMocks
  private FakeHsaGetUnitIntegrationService fakeHsaGetUnitIntegrationService;

  @Test
  void shouldThrowIfHsaIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> fakeHsaGetUnitIntegrationService.get(
        GetUnitIntegrationRequest.builder().build()
    ));
  }


  @Test
  void shouldThrowIfHsaIdIsEmpty() {
    assertThrows(IllegalArgumentException.class, () -> fakeHsaGetUnitIntegrationService.get(
        GetUnitIntegrationRequest.builder().hsaId("").build()
    ));
  }

  @Test
  void shouldReturnUnitFromFakeHsaRepository() {
    final var request = GetUnitIntegrationRequest.builder()
        .hsaId(HSA_ID)
        .build();

    final var expectedUnit = Unit.builder().build();

    when(fakeHsaRepository.getUnit(request.getHsaId())).thenReturn(expectedUnit);

    final var result = fakeHsaGetUnitIntegrationService.get(request);

    assertEquals(expectedUnit, result.getUnit());
  }
}
