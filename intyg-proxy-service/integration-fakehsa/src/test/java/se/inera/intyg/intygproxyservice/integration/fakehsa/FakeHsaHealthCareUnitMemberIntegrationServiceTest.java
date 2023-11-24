package se.inera.intyg.intygproxyservice.integration.fakehsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.FakeHsaRepository;

@ExtendWith(MockitoExtension.class)
class FakeHsaHealthCareUnitMemberIntegrationServiceTest {

    @Mock
    private FakeHsaRepository fakeHsaRepository;
    @InjectMocks
    private FakeHsaHealthCareUnitMemberIntegrationService healthCareUnitMemberIntegrationService;

    private static final String HSA_ID = "hsaId";
    private static final String EMPTY = "";

    @Test
    void shouldReturnHealthCareUnitMembersResponse() {
        final var request = GetHealthCareUnitMembersIntegrationRequest.builder().hsaId(HSA_ID).build();
        final var healthCareUnitMembers = HealthCareUnitMembers.builder().build();
        final var expectedResponse = GetHealthCareUnitMembersIntegrationResponse.builder()
            .healthCareUnitMembers(healthCareUnitMembers)
            .build();

        when(fakeHsaRepository.getHealthCareUnitMembers(request.getHsaId())).thenReturn(
            healthCareUnitMembers);

        final var result = healthCareUnitMemberIntegrationService.get(
            request);
        assertEquals(expectedResponse, result);
    }

    @Test
    void shouldThrowIlligalArgumentExceptionIfHsaIdIsNotProvided() {
        final var request = GetHealthCareUnitMembersIntegrationRequest.builder().build();
        assertThrows(IllegalArgumentException.class,
            () -> healthCareUnitMemberIntegrationService.get(request));
    }

    @Test
    void shouldThrowIlligalArgumentExceptionIfHsaIdIsEmpty() {
        final var request = GetHealthCareUnitMembersIntegrationRequest.builder().hsaId(EMPTY).build();
        assertThrows(IllegalArgumentException.class,
            () -> healthCareUnitMemberIntegrationService.get(request));
    }
}