package se.inera.intyg.intygproxyservice.authorization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialsForPersonRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;

@ExtendWith(MockitoExtension.class)
class CredentialsForPersonServiceTest {

  private static final String PERSON_ID = "PERSON_ID";

  private static final CredentialsForPersonRequest REQUEST = CredentialsForPersonRequest
      .builder()
      .personId(PERSON_ID)
      .build();

  private static final GetCredentialsForPersonIntegrationResponse RESPONSE = GetCredentialsForPersonIntegrationResponse
      .builder()
      .credentials(CredentialsForPerson.builder().build())
      .build();

  @Mock
  private GetCredentialsForPersonIntegrationService getCredentialsForPersonIntegrationService;

  @InjectMocks
  private CredentialsForPersonService credentialsForPersonService;

  @Test
  void shouldThrowIllegalArgumentExceptionIfRequestIsNull() {
    assertThrows(IllegalArgumentException.class, () -> credentialsForPersonService.get(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfPersonIdIsNull() {
    final var request = CredentialsForPersonRequest.builder().build();
    assertThrows(IllegalArgumentException.class, () -> credentialsForPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfpersonIdIsEmpty() {
    final var request = CredentialsForPersonRequest.builder().personId("").build();
    assertThrows(IllegalArgumentException.class, () -> credentialsForPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfpersonIdIsBlank() {
    final var request = CredentialsForPersonRequest.builder().personId("   ").build();
    assertThrows(IllegalArgumentException.class, () -> credentialsForPersonService.get(request));
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setUp() {
      when(getCredentialsForPersonIntegrationService.get(
          any(GetCredentialsForPersonIntegrationRequest.class)))
          .thenReturn(RESPONSE);
    }

    @Test
    void shallReturnCredentialInformation() {
      final var response = credentialsForPersonService.get(REQUEST);

      assertEquals(RESPONSE.getCredentials(), response.getCredentials());
    }

    @Test
    void shallSetPersonIdInRequest() {
      credentialsForPersonService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(GetCredentialsForPersonIntegrationRequest.class);
      verify(getCredentialsForPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getPersonId(), captor.getValue().getPersonId());
    }
  }
}