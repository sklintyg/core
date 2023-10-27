package se.inera.intyg.intygproxyservice.employee;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.employee.dto.EmployeeRequest;
import se.inera.intyg.intygproxyservice.employee.dto.EmployeeResponse;
import se.inera.intyg.intygproxyservice.employee.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void shallReturnEmployeeResponseWhenCallingGetEmployee() {
        final var expectedResponse = EmployeeResponse.builder().build();
        when(employeeService.getEmployee(any(EmployeeRequest.class)))
            .thenReturn(expectedResponse);

        final var response = employeeController.getEmployee(
            EmployeeRequest.builder().build()
        );

        assertEquals(expectedResponse, response);
    }
}