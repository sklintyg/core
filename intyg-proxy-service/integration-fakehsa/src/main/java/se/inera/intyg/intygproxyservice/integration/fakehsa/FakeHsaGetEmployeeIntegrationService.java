package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationService;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@Service
@AllArgsConstructor
@Profile(FAKE_HSA_PROFILE)
public class FakeHsaGetEmployeeIntegrationService implements GetEmployeeIntegrationService {

  private final FakeHsaRepository fakeHsaRepository;

  @Override
  public GetEmployeeIntegrationResponse get(
      GetEmployeeIntegrationRequest getEmployeeIntegrationRequest) {
    validateRequestParameters(getEmployeeIntegrationRequest);

    final var employee = fakeHsaRepository.getEmployee(
        getIdFromRequest(getEmployeeIntegrationRequest)
    );

    return GetEmployeeIntegrationResponse.builder()
        .employee(employee)
        .build();
  }

  private String getIdFromRequest(GetEmployeeIntegrationRequest getEmployeeIntegrationRequest) {
    return getEmployeeIntegrationRequest.getHsaId() != null
        ? getEmployeeIntegrationRequest.getHsaId() : getEmployeeIntegrationRequest.getPersonId();
  }

  private void validateRequestParameters(GetEmployeeIntegrationRequest getEmployeeRequestDTO) {
    if (isNullOrEmpty(getEmployeeRequestDTO.getHsaId()) && isNullOrEmpty(
        getEmployeeRequestDTO.getPersonId())) {
      throw new IllegalArgumentException(
          String.format(
              "Missing required parameters. personId: '%s' and hsaId '%s'",
              getEmployeeRequestDTO.getPersonId(), getEmployeeRequestDTO.getHsaId()));
    }
    if (!isNullOrEmpty(getEmployeeRequestDTO.getHsaId()) && !isNullOrEmpty(
        getEmployeeRequestDTO.getPersonId())) {
      throw new IllegalArgumentException(
          "Only provide either personalIdentityNumber or personHsaId. ");
    }
  }

  private boolean isNullOrEmpty(String value) {
    return value == null || value.isEmpty();
  }
}
