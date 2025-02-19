/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.inera.intyg.intygproxyservice.employee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.employee.dto.EmployeeRequest;
import se.inera.intyg.intygproxyservice.employee.service.EmployeeService;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationService;
import se.inera.intyg.intygproxyservice.logging.LogHashUtility;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  public static final String HSA_ID = "HSA_ID";
  public static final String PERSON_ID = "PERSON_ID";
  private static final EmployeeRequest REQUEST = EmployeeRequest
      .builder()
      .hsaId(HSA_ID)
      .personId(PERSON_ID)
      .build();

  private static final GetEmployeeIntegrationResponse RESPONSE = GetEmployeeIntegrationResponse
      .builder()
      .employee(Employee
          .builder()
          .personInformation(Collections.emptyList())
          .build())
      .build();

  @Mock
  private GetEmployeeIntegrationService getEmployeeIntegrationService;
  @Mock
  private LogHashUtility logHashUtility;

  @InjectMocks
  private EmployeeService employeeService;


  @Nested
  class RequestValidation {

    @Test
    void shallThrowExceptionIfRequestIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> employeeService.getEmployee(null)
      );
    }

    @Test
    void shallThrowExceptionIfRequestContainsNullPersonIdAndHsaId() {
      final var request = EmployeeRequest.builder().build();

      assertThrows(IllegalArgumentException.class,
          () -> employeeService.getEmployee(request)
      );
    }

    @Test
    void shallThrowExceptionIfRequestContainsEmptyPersonIdAndHsaId() {
      final var request = EmployeeRequest.builder()
          .personId("")
          .hsaId("")
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> employeeService.getEmployee(request)
      );
    }

    @Test
    void shallThrowExceptionIfRequestContainsBlankPersonIdAndHsaId() {
      final var request = EmployeeRequest.builder()
          .personId("  ")
          .hsaId("   ")
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> employeeService.getEmployee(request)
      );
    }

    @Nested
    class ValidRequest {

      @BeforeEach
      void setUp() {
        when(getEmployeeIntegrationService.get(any(GetEmployeeIntegrationRequest.class)))
            .thenReturn(RESPONSE);
      }

      @Test
      void shallNotThrowExceptionIfRequestContainsOnlyBlankHsaId() {
        final var request = EmployeeRequest
            .builder()
            .personId(PERSON_ID)
            .hsaId("  ")
            .build();

        assertDoesNotThrow(() -> employeeService.getEmployee(request));
      }

      @Test
      void shallNotThrowExceptionIfRequestContainsOnlyBlankPersonId() {
        final var request = EmployeeRequest
            .builder()
            .personId(" ")
            .hsaId(HSA_ID)
            .build();

        assertDoesNotThrow(() -> employeeService.getEmployee(request));
      }

      @Test
      void shallNotThrowExceptionIfRequestContainsOnlyEmptyHsaId() {
        final var request = EmployeeRequest
            .builder()
            .personId(PERSON_ID)
            .hsaId("")
            .build();

        assertDoesNotThrow(() -> employeeService.getEmployee(request));
      }

      @Test
      void shallNotThrowExceptionIfRequestContainsOnlyNullPersonId() {
        final var request = EmployeeRequest
            .builder()
            .hsaId(HSA_ID)
            .build();

        assertDoesNotThrow(() -> employeeService.getEmployee(request));
      }

      @Test
      void shallNotThrowExceptionIfRequestContainsOnlyNullHsaId() {
        final var request = EmployeeRequest
            .builder()
            .personId(PERSON_ID)
            .build();

        assertDoesNotThrow(() -> employeeService.getEmployee(request));
      }

      @Test
      void shallNotThrowExceptionIfRequestContainsOnlyEmptyPersonId() {
        final var request = EmployeeRequest
            .builder()
            .personId("")
            .hsaId(HSA_ID)
            .build();

        assertDoesNotThrow(() -> employeeService.getEmployee(request));
      }
    }
  }

  @Nested
  class Response {

    @BeforeEach
    void setUp() {
      when(getEmployeeIntegrationService.get(any(GetEmployeeIntegrationRequest.class)))
          .thenReturn(RESPONSE);
    }

    @Test
    void shallReturnEmployee() {
      final var response = employeeService.getEmployee(REQUEST);

      assertEquals(RESPONSE.getEmployee(), response.getEmployee());
    }

    @Test
    void shallSetHsaIdInRequest() {
      employeeService.getEmployee(REQUEST);

      final var captor = ArgumentCaptor.forClass(GetEmployeeIntegrationRequest.class);
      verify(getEmployeeIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getHsaId(), captor.getValue().getHsaId());
    }

    @Test
    void shallSetPersonIdInRequest() {
      employeeService.getEmployee(REQUEST);

      final var captor = ArgumentCaptor.forClass(GetEmployeeIntegrationRequest.class);
      verify(getEmployeeIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getPersonId(), captor.getValue().getPersonId());
    }
  }
}
