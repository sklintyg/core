package se.inera.intyg.certificateservice.application.certificate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.EraseCertificateInternalRequest;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class EraseCertificateInternalForCareProviderServiceTest {

    private static final String CARE_PROVIDER_ID = "careProviderId";
    @Mock
    CertificateRepository certificateRepository;
    @InjectMocks
    EraseCertificateInternalForCareProviderService eraseCertificateInternalForCareProviderService;

    @Test
    void shallDeleteByCareProviderId() {
        final var request = EraseCertificateInternalRequest.builder()
            .batchSize(5)
            .build();

        eraseCertificateInternalForCareProviderService.erase(request, CARE_PROVIDER_ID);

        verify(certificateRepository).deleteByCareProviderId(CARE_PROVIDER_ID, request.getBatchSize());
    }

    @Test
    @ExtendWith(OutputCaptureExtension.class)
    void shallLog(CapturedOutput output) {
        final var request = EraseCertificateInternalRequest.builder()
            .batchSize(5)
            .build();

        doReturn(5L).when(certificateRepository).deleteByCareProviderId(CARE_PROVIDER_ID, request.getBatchSize());

        eraseCertificateInternalForCareProviderService.erase(request, CARE_PROVIDER_ID);
        assertThat(output).contains("Successfully completed erasure of certificates for care provider 'careProviderId' Total number of erased certificates: 5");
    }
}