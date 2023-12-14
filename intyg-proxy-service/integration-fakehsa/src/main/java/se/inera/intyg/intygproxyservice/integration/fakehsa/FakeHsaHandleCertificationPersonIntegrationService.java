package se.inera.intyg.intygproxyservice.integration.fakehsa;


import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;

@Service
@Profile(FAKE_HSA_PROFILE)
@RequiredArgsConstructor
public class FakeHsaHandleCertificationPersonIntegrationService implements
    HandleCertificationPersonIntegrationService {

  @Override
  public HandleCertificationPersonIntegrationResponse get(
      HandleCertificationPersonIntegrationRequest request) {
    return HandleCertificationPersonIntegrationResponse.builder()
        .result(
            Result.builder()
                .resultCode("OK")
                .resultText("OK")
                .build()
        )
        .build();
  }
}
