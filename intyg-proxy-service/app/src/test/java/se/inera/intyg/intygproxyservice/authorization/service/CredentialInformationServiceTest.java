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
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialInformationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;

@ExtendWith(MockitoExtension.class)
class CredentialInformationServiceTest {

  private static final String HSA_ID = "HSA_ID";

  private static final CredentialInformationRequest REQUEST = CredentialInformationRequest
      .builder()
      .personHsaId(HSA_ID)
      .build();

  private static final GetCredentialInformationIntegrationResponse RESPONSE = GetCredentialInformationIntegrationResponse
      .builder()
      .credentialInformation(CredentialInformation.builder().build())
      .build();

  @Mock
  private GetCredentialInformationIntegrationService getCredentialInformationIntegrationService;

  @InjectMocks
  private CredentialInformationService credentialInformationService;

  @Test
  void shouldThrowIllegalArgumentExceptionIfRequestIsNull() {
    assertThrows(IllegalArgumentException.class, () -> credentialInformationService.get(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfPersonHsaIdIsNull() {
    final var request = CredentialInformationRequest.builder().build();
    assertThrows(IllegalArgumentException.class, () -> credentialInformationService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfPersonHsaIdIsEmpty() {
    final var request = CredentialInformationRequest.builder().personHsaId("").build();
    assertThrows(IllegalArgumentException.class, () -> credentialInformationService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfPersonHsaIdIsBlank() {
    final var request = CredentialInformationRequest.builder().personHsaId("   ").build();
    assertThrows(IllegalArgumentException.class, () -> credentialInformationService.get(request));
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setUp() {
      when(getCredentialInformationIntegrationService.get(
          any(GetCredentialInformationIntegrationRequest.class)))
          .thenReturn(RESPONSE);
    }

    @Test
    void shallReturnCredentialInformation() {
      final var response = credentialInformationService.get(REQUEST);

      assertEquals(RESPONSE.getCredentialInformation(), response.getCredentialInformation());
    }

    @Test
    void shallSetPersonHsaIdInRequest() {
      credentialInformationService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(GetCredentialInformationIntegrationRequest.class);
      verify(getCredentialInformationIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getPersonHsaId(), captor.getValue().getPersonHsaId());
    }
  }
}