package se.inera.intyg.intygproxyservice.integration.employee;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationService;

@Service
@Slf4j
@RequiredArgsConstructor
public class HsaGetEmployeeIntegrationIntegrationService implements GetEmployeeIntegrationService {

  @Override
  public GetEmployeeIntegrationResponse get(
      GetEmployeeIntegrationRequest getEmployeeIntegrationRequest) {
    return GetEmployeeIntegrationResponse
        .builder()
        .employee(Employee
            .builder()
            .personalInformation(Collections.emptyList())
            .build())
        .build();
  }
}
