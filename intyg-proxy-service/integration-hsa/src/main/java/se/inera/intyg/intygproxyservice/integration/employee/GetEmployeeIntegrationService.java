package se.inera.intyg.intygproxyservice.integration.employee;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeRequest;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeResponse;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeService;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetEmployeeIntegrationService implements GetEmployeeService {

    @Override
    public GetEmployeeResponse get(GetEmployeeRequest getEmployeeRequest) {
        return GetEmployeeResponse
            .builder()
            .employee(Employee
                .builder()
                .personalInformation(Collections.emptyList())
                .build())
            .build();
    }
}
