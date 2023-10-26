package se.inera.intyg.intygproxyservice.employee;

import lombok.Builder;
import lombok.Data;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;

@Data
@Builder
public class EmployeeResponse {

  private Employee employee;

}
