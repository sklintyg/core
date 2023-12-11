package se.inera.intyg.intygproxyservice.integration.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.authorization.client.HsaAuthorizationClient;

@ExtendWith(MockitoExtension.class)
class HsaGetCredentialsForPersonIntegrationServiceTest {

  @Mock
  HsaAuthorizationClient hsaAuthorizationClient;

  @InjectMocks
  HsaGetCredentialsForPersonIntegrationService getCredentialsForPersonIntegrationService;

  @Test
  void shouldReturnValueFromClient() {
    final var expected = CredentialsForPerson.builder().build();
    when(hsaAuthorizationClient.getCredentialsForPerson(
        any(GetCredentialsForPersonIntegrationRequest.class))
    ).thenReturn(expected);

    final var response = getCredentialsForPersonIntegrationService.get(
        GetCredentialsForPersonIntegrationRequest.builder().build());

    assertEquals(expected, response.getCredentials());
  }

}