package se.inera.intyg.intygproxyservice.employee;

import static org.junit.jupiter.api.Assertions.*;

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

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    public static final String HSA_ID = "HSA_ID";
    public static final String PERSON_ID = "PERSON_ID";
    private static final EmployeeRequest REQUEST = EmployeeRequest
        .builder()
        .personId(PERSON_ID)
        .hsaId(HSA_ID)
        .build();

    private static final GetEmployeeIntegrationResponse RESPONSE = GetEmployeeIntegrationResponse
        .builder()
        .employee(Employee
            .builder()
            .personalInformation(Collections.emptyList())
            .build())
        .build();

    @Mock
    private GetEmployeeIntegrationService getEmployeeIntegrationService;

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