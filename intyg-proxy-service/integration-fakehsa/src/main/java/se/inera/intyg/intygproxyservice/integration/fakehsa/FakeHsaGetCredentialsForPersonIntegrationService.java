package se.inera.intyg.intygproxyservice.integration.fakehsa;


import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@Service
@Profile(FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class FakeHsaGetCredentialsForPersonIntegrationService implements
    GetCredentialsForPersonIntegrationService {

  private final FakeHsaRepository fakeHsaRepository;

  @Override
  public GetCredentialsForPersonIntegrationResponse get(
      GetCredentialsForPersonIntegrationRequest request) {
    validateRequest(request);
    final var response = fakeHsaRepository.getCredentialsForPerson(request.getPersonId());

    return GetCredentialsForPersonIntegrationResponse.builder()
        .credentials(response)
        .build();
  }

  private void validateRequest(GetCredentialsForPersonIntegrationRequest request) {
    final var personId = request.getPersonId();

    if (personId == null || personId.isEmpty()) {
      throw new IllegalArgumentException("Missing required parameter 'personId'");
    }
  }
}
