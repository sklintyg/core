package se.inera.intyg.intygproxyservice.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeRequest;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeService;

@Service
@AllArgsConstructor
public class EmployeeService {

  private final GetEmployeeService getEmployeeService;

  public EmployeeResponse getEmployee(EmployeeRequest request) {
    validateRequest(request);

    final var response = getEmployeeService.get(
        GetEmployeeRequest.builder()
            .hsaId(request.getHsaId())
            .personId(request.getPersonId())
            .build()
    );

    return EmployeeResponse
        .builder()
        .employee(response.getEmployee())
        .build();
  }

  private static void validateRequest(EmployeeRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to get employee is null");
    }

    if (isStringInvalid(request.getPersonId()) && isStringInvalid(request.getHsaId())) {
      throw new IllegalArgumentException(
          String.format("PersonId and HsaId are not valid: '%s', '%s'", request.getPersonId(), request.getHsaId())
      );
    }
  }

  private static boolean isStringInvalid(String value) {
    return value == null || value.isBlank() || value.isEmpty();
  }
}
