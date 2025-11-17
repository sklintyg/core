package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@ExtendWith(MockitoExtension.class)
class DeleteDraftsDomainServiceTest {

    private static final String ID = "ID";
    @Mock
    private CertificateRepository certificateRepository;
    @InjectMocks
    private DeleteDraftsDomainService service;

    private final LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1);
    private final CertificatesRequest request = CertificatesRequest.builder()
        .createdTo(cutoffDate)
        .statuses(List.of(Status.DRAFT, Status.DELETED_DRAFT, Status.LOCKED_DRAFT))
        .build();

    @Test
    void shouldFindDraftsToDelete() {
        final var certificate = mock(MedicalCertificate.class);
        doReturn(List.of(certificate)).when(certificateRepository).findByCertificatesRequest(request);

        service.delete(cutoffDate);

        verify(certificateRepository).findByCertificatesRequest(request);
    }

    @Test
    void shouldRemoveAllDrafts() {
        final var certificate = mock(MedicalCertificate.class);
        when(certificate.id())
            .thenReturn(new CertificateId(ID));
        doReturn(List.of(certificate)).when(certificateRepository).findByCertificatesRequest(request);

        service.delete(cutoffDate);

        verify(certificateRepository).remove(List.of(new CertificateId(ID)));
    }

    @Test
    void shouldReturnDeletedDrafts() {
        final var certificate = mock(MedicalCertificate.class);
        final var certificates = List.of(certificate);

        doReturn(certificates).when(certificateRepository).findByCertificatesRequest(request);

        final var deletedCertificates = service.delete(cutoffDate);

        assertEquals(certificates, deletedCertificates);
    }

    @Test
    void shouldNotRemoveAllIfNoCertificatesFound() {
        doReturn(List.of()).when(certificateRepository).findByCertificatesRequest(request);

        service.delete(cutoffDate);

        verify(certificateRepository).findByCertificatesRequest(request);
    }
}

