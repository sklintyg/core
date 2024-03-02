package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaGetCredentialInformationIntegrationServiceTest {

  private static final String HSA_ID = "hsaId";
  @Mock
  private FakeHsaRepository fakeHsaRepository;

  @InjectMocks
  private FakeHsaGetCredentialInformationIntegrationService fakeHsaGetUnitIntegrationService;

  @Test
  void shouldThrowIfHsaIdIsNull() {
    final var request = GetCredentialInformationIntegrationRequest.builder().build();
    assertThrows(IllegalArgumentException.class,
        () -> fakeHsaGetUnitIntegrationService.get(request)
    );
  }


  @Test
  void shouldThrowIfHsaIdIsEmpty() {
    final var request = GetCredentialInformationIntegrationRequest.builder()
        .personHsaId("")
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> fakeHsaGetUnitIntegrationService.get(request)
    );
  }

  @Test
  void shouldReturnCredentialInformationFromFakeHsaRepository() {
    final var request = GetCredentialInformationIntegrationRequest.builder()
        .personHsaId(HSA_ID)
        .build();

    final var expectedResult = List.of(CredentialInformation.builder().build());

    when(fakeHsaRepository.getCredentialInformation(request.getPersonHsaId())).thenReturn(
        expectedResult);

    final var result = fakeHsaGetUnitIntegrationService.get(request);

    assertEquals(expectedResult, result.getCredentialInformation());
  }
}
