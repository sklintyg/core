package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GetHealthCareUnitIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaHealthCareUnitIntegrationServiceTest {

  @Mock
  private FakeHsaRepository fakeHsaRepository;
  @InjectMocks
  private FakeHsaHealthCareUnitIntegrationService healthCareUnitIntegrationService;

  private static final String HSA_ID = "hsaId";
  private static final String EMPTY = "";

  @Test
  void shouldReturnHealthCareUnitMembersResponse() {
    final var request = GetHealthCareUnitIntegrationRequest.builder().hsaId(HSA_ID).build();
    final var healthCareUnitMembers = HealthCareUnit.builder().build();
    final var expectedResponse = GetHealthCareUnitIntegrationResponse.builder()
        .healthCareUnit(healthCareUnitMembers)
        .build();

    when(fakeHsaRepository.getHealthCareUnit(request.getHsaId())).thenReturn(
        healthCareUnitMembers);

    final var result = healthCareUnitIntegrationService.get(
        request);
    assertEquals(expectedResponse, result);
  }

  @Test
  void shouldThrowIlligalArgumentExceptionIfHsaIdIsNotProvided() {
    final var request = GetHealthCareUnitIntegrationRequest.builder().build();
    assertThrows(IllegalArgumentException.class,
        () -> healthCareUnitIntegrationService.get(request));
  }

  @Test
  void shouldThrowIlligalArgumentExceptionIfHsaIdIsEmpty() {
    final var request = GetHealthCareUnitIntegrationRequest.builder().hsaId(EMPTY).build();
    assertThrows(IllegalArgumentException.class,
        () -> healthCareUnitIntegrationService.get(request));
  }
}
