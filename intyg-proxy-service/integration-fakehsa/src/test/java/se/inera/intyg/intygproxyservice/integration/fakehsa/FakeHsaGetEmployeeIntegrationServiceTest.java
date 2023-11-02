package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaGetEmployeeIntegrationServiceTest {

  @Mock
  private FakeHsaRepository fakeHsaRepository;
  @InjectMocks
  private FakeHsaGetEmployeeIntegrationService fakeHsaGetEmployeeIntegrationService;

  private static final String EMPTY = "";
  private static final String HSA_ID = "hsaId";

  @Test
  void shouldReturnGetEmployeeResponse() {
    final var request = GetEmployeeIntegrationRequest.builder()
        .hsaId(HSA_ID)
        .build();
    final var employee = Employee.builder().build();
    final var expectedResponse = GetEmployeeIntegrationResponse.builder()
        .employee(employee)
        .build();
    when(fakeHsaRepository.getEmployee(request.getHsaId())).thenReturn(employee);
    final var result = fakeHsaGetEmployeeIntegrationService.get(request);
    assertEquals(expectedResponse, result);
  }

  @Test
  void shouldThrowIfHsaIdAndPersonIdFromRequestIsNull() {
    final var request = GetEmployeeIntegrationRequest.builder().build();
    assertThrows(IllegalArgumentException.class,
        () -> fakeHsaGetEmployeeIntegrationService.get(request));
  }

  @Test
  void shouldThrowIfHsaIdAndPersonIdFromRequestIsEmpty() {
    final var request = GetEmployeeIntegrationRequest.builder()
        .hsaId(EMPTY)
        .personId(EMPTY)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> fakeHsaGetEmployeeIntegrationService.get(request));
  }
}
