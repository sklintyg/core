package se.inera.intyg.intygproxyservice.integration.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;
import se.inera.intyg.intygproxyservice.integration.authorization.client.HsaAuthorizationClient;

@ExtendWith(MockitoExtension.class)
class HsaHandleCertificationPersonIntegrationServiceTest {

  @Mock
  HsaAuthorizationClient hsaAuthorizationClient;

  @InjectMocks
  HsaHandleCertificationPersonIntegrationService hsaHandleCertificationPersonIntegrationService;

  @Test
  void shouldReturnResponseFromClient() {
    final var expected = Result.builder().build();
    when(hsaAuthorizationClient.handleCertificationPerson(
        any(HandleCertificationPersonIntegrationRequest.class)))
        .thenReturn(expected);

    final var response = hsaHandleCertificationPersonIntegrationService.get(
        HandleCertificationPersonIntegrationRequest.builder().build());

    assertEquals(expected, response.getResult());
  }

}