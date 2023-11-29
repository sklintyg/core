package se.inera.intyg.intygproxyservice.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialInformationRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialInformationResponse;
import se.inera.intyg.intygproxyservice.authorization.service.CredentialInformationService;

@ExtendWith(MockitoExtension.class)
class CredentialInformationControllerTest {

  @Mock
  private CredentialInformationService credentialInformationService;

  @InjectMocks
  private CredentialInformationController controller;

  @Test
  void shallReturnCredentialInformationResponseWhenCallingGetCredentialInformation() {
    final var expectedResponse = CredentialInformationResponse.builder().build();
    when(credentialInformationService.get(any(CredentialInformationRequest.class)))
        .thenReturn(expectedResponse);

    final var response = controller.getCredentialInformation(
        CredentialInformationRequest.builder().build()
    );

    assertEquals(expectedResponse, response);
  }
}