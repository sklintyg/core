package se.inera.intyg.intygproxyservice.integration.api.employee;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetEmployeeResponse {

  Employee employee;
}
