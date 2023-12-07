package se.inera.intyg.intygproxyservice.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialsForPersonRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialsForPersonResponse;
import se.inera.intyg.intygproxyservice.authorization.service.CredentialsForPersonService;

@ExtendWith(MockitoExtension.class)
class CredentialsForPersonControllerTest {

  @Mock
  private CredentialsForPersonService credentialsForPersonService;

  @InjectMocks
  private CredentialsForPersonController credentialsForPersonController;

  @Test
  void shallReturnCredentialInformationResponseWhenCallingGetCredentialInformation() {
    final var expectedResponse = CredentialsForPersonResponse.builder().build();
    when(credentialsForPersonService.get(any(CredentialsForPersonRequest.class)))
        .thenReturn(expectedResponse);

    final var response = credentialsForPersonController.getCredentialsForPerson(
        CredentialsForPersonRequest.builder().build()
    );

    assertEquals(expectedResponse, response);
  }
}