package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaGetCredentialsForPersonIntegrationServiceTest {

  private static final String PERSON_ID = "personId";
  @Mock
  private FakeHsaRepository fakeHsaRepository;

  @InjectMocks
  private FakeHsaGetCredentialsForPersonIntegrationService credentialsForPersonIntegrationService;

  @Test
  void shouldThrowIfParameterPersonIdIsMissing() {
    assertThrows(IllegalArgumentException.class, () ->
        credentialsForPersonIntegrationService.get(
            GetCredentialsForPersonIntegrationRequest.builder().build()));
  }


  @Test
  void shouldThrowIfParameterPersonIdIsEmpty() {
    assertThrows(IllegalArgumentException.class, () ->
        credentialsForPersonIntegrationService.get(
            GetCredentialsForPersonIntegrationRequest.builder().personId("").build()));
  }

  @Test
  void shouldReturnResponseWithCredentialsForPerson() {
    final var credentialsForPerson = CredentialsForPerson.builder().build();
    when(fakeHsaRepository.getCredentialsForPerson(PERSON_ID)).thenReturn(
        credentialsForPerson
    );

    final var expectedResult = GetCredentialsForPersonIntegrationResponse.builder()
        .credentials(credentialsForPerson)
        .build();

    final var request = GetCredentialsForPersonIntegrationRequest.builder()
        .personId(PERSON_ID)
        .build();

    final var result = credentialsForPersonIntegrationService.get(
        request);

    assertEquals(expectedResult, result);
  }
}
