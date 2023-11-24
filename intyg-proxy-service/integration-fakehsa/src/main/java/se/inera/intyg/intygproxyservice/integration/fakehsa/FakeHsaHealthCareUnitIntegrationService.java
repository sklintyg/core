package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@Service
@AllArgsConstructor
@Profile(FAKE_HSA_PROFILE)
public class FakeHsaHealthCareUnitIntegrationService implements
    GetHealthCareUnitIntegrationService {

  private final FakeHsaRepository fakeHsaRepository;

  @Override
  public GetHealthCareUnitIntegrationResponse get(GetHealthCareUnitIntegrationRequest request) {
    validateRequest(request);
    return GetHealthCareUnitIntegrationResponse.builder()
        .healthCareUnit(fakeHsaRepository.getHealthCareUnit(request.getHsaId()))
        .build();
  }

  private void validateRequest(GetHealthCareUnitIntegrationRequest request) {
    if (request.getHsaId() == null || request.getHsaId().isEmpty()) {
      throw new IllegalArgumentException(
          String.format("Request did not contain a valid hsaId: '%s'", request.getHsaId()));
    }
  }
}
