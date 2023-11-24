package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@Service
@AllArgsConstructor
@Profile(FAKE_HSA_PROFILE)
public class FakeHsaHealthCareUnitMemberIntegrationService implements
    GetHealthCareUnitMembersIntegrationService {

  private final FakeHsaRepository fakeHsaRepository;

  @Override
  public GetHealthCareUnitMembersIntegrationResponse get(
      GetHealthCareUnitMembersIntegrationRequest request) {
    validateRequest(request);
    return GetHealthCareUnitMembersIntegrationResponse.builder()
        .healthCareUnitMembers(fakeHsaRepository.getHealthCareUnitMembers(request.getHsaId()))
        .build();
  }

  private void validateRequest(GetHealthCareUnitMembersIntegrationRequest request) {
    if (request.getHsaId() == null || request.getHsaId().isEmpty()) {
      throw new IllegalArgumentException(
          String.format("Request did not contain a valid hsaId: '%s'", request.getHsaId()));
    }
  }
}
