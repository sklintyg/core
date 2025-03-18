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
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;

@ExtendWith(MockitoExtension.class)
class EraseCertificateInternalForCareProviderServiceTest {

    private static final String CARE_PROVIDER_ID = "careProviderId";
    @Mock
    CertificateRepository certificateRepository;
    @InjectMocks
    EraseCertificateInternalForCareProviderService eraseCertificateInternalForCareProviderService;

    @Test
    void shallDeleteByCareProviderId() {
        eraseCertificateInternalForCareProviderService.erase(CARE_PROVIDER_ID);

        verify(certificateRepository).deleteByCareProviderId(new HsaId(CARE_PROVIDER_ID));
    }

    @Test
    @ExtendWith(OutputCaptureExtension.class)
    void shallLog(CapturedOutput output) {
        doReturn(5L).when(certificateRepository).deleteByCareProviderId(new HsaId(CARE_PROVIDER_ID));

        eraseCertificateInternalForCareProviderService.erase(CARE_PROVIDER_ID);
        assertThat(output).contains("Successfully completed erasure of certificates for care provider 'careProviderId' Total number of erased certificates: 5");
    }
}