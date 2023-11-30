package se.inera.intyg.intygproxyservice.integration.fakehsa;


import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationService;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@Service
@Profile(FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class FakeHsaGetCredentialInformationIntegrationService implements
    GetCredentialInformationIntegrationService {

  private final FakeHsaRepository fakeHsaRepository;

  @Override
  public GetCredentialInformationIntegrationResponse get(
      GetCredentialInformationIntegrationRequest request) {
    validateRequestParameters(request);
    final var response = fakeHsaRepository.getCredentialInformation(request.getPersonHsaId());

    return GetCredentialInformationIntegrationResponse.builder()
        .credentialInformation(response)
        .build();
  }

  private void validateRequestParameters(GetCredentialInformationIntegrationRequest request) {
    if (request.getPersonHsaId() == null || request.getPersonHsaId().isEmpty()) {
      throw new IllegalArgumentException(
          String.format("Request did not contain a valid hsaId: '%s'", request.getPersonHsaId()));
    }
  }
}
