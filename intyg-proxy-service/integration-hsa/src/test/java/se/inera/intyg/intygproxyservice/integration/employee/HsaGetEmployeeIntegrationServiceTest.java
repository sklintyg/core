package se.inera.intyg.intygproxyservice.integration.employee;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;

@ExtendWith(MockitoExtension.class)
class HsaGetEmployeeIntegrationServiceTest {

    public static final String HSA_ID = "HSA_ID";
    @InjectMocks
    HsaGetEmployeeIntegrationIntegrationService hsaGetEmployeeIntegrationService;

    @Test
    void shouldReturnEmployee() {
        final var response = hsaGetEmployeeIntegrationService.get(
            GetEmployeeIntegrationRequest
            .builder()
            .hsaId(HSA_ID)
            .build());

        assertNotNull(response);
    }

}