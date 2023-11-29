package se.inera.intyg.intygproxyservice.integration.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.authorization.client.HsaAuthorizationClient;

@ExtendWith(MockitoExtension.class)
class HsaGetCredentialInformationIntegrationServiceTest {

  public static final List<CredentialInformation> INFO = List.of(
      CredentialInformation.builder().build());
  @Mock
  HsaAuthorizationClient hsaAuthorizationClient;

  @InjectMocks
  HsaGetCredentialInformationIntegrationService hsaGetCredentialInformationIntegrationService;

  @Test
  void shouldReturnValue() {
    when(hsaAuthorizationClient.getCredentialInformation(any(
        GetCredentialInformationIntegrationRequest.class)))
        .thenReturn(INFO);
    final var response = hsaGetCredentialInformationIntegrationService.get(
        GetCredentialInformationIntegrationRequest
            .builder()
            .build()
    );

    assertEquals(INFO, response.getCredentialInformation());
  }
}