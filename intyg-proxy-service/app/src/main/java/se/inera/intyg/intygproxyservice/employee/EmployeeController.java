package se.inera.intyg.intygproxyservice.employee;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.employee.dto.EmployeeRequest;
import se.inera.intyg.intygproxyservice.employee.dto.EmployeeResponse;
import se.inera.intyg.intygproxyservice.employee.service.EmployeeService;

@RestController()
@RequestMapping("/api/v2/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("")
    EmployeeResponse findEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.getEmployee(employeeRequest);
    }
}
