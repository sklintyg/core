package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
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
class DeleteStaleDraftsDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @InjectMocks
  private DeleteStaleDraftsDomainService service;

  @Nested
  class ListTests {

    private final LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1);

    @Test
    void shouldFindUnsignedDraftsBeforeCutoffDate() {
      final var certificate = mock(MedicalCertificate.class);
      final var expectedRequest = CertificatesRequest.builder()
          .createdTo(cutoffDate)
          .statuses(List.of(Status.DRAFT, Status.DELETED_DRAFT, Status.LOCKED_DRAFT))
          .build();

      doReturn(List.of(certificate)).when(certificateRepository)
          .findByCertificatesRequest(expectedRequest);

      final var result = service.list(cutoffDate);

      assertEquals(List.of(certificate), result);
      verify(certificateRepository).findByCertificatesRequest(expectedRequest);
    }

    @Test
    void shouldReturnEmptyListWhenNoDraftsFound() {
      final var expectedRequest = CertificatesRequest.builder()
          .createdTo(cutoffDate)
          .statuses(List.of(Status.DRAFT, Status.DELETED_DRAFT, Status.LOCKED_DRAFT))
          .build();

      doReturn(Collections.emptyList()).when(certificateRepository)
          .findByCertificatesRequest(expectedRequest);

      final var result = service.list(cutoffDate);

      assertEquals(Collections.emptyList(), result);
    }
  }

  @Nested
  class DeleteByIdTests {

    private static final CertificateId CERT_ID_1 = new CertificateId("cert-123");

    @Test
    void shouldFindCertificateById() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.DRAFT);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(List.of(CERT_ID_1));

      service.delete(CERT_ID_1);

      verify(certificateRepository).findByIds(List.of(CERT_ID_1));
    }

    @Test
    void shouldValidateCertificateHasValidStatus() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.DRAFT);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(List.of(CERT_ID_1));

      service.delete(CERT_ID_1);

      verify(certificateRepository).remove(List.of(CERT_ID_1));
    }

    @Test
    void shouldThrowExceptionWhenCertificateHasInvalidStatus() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.SIGNED);
      when(certificate.id()).thenReturn(CERT_ID_1);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(List.of(CERT_ID_1));

      final var exception = assertThrows(IllegalStateException.class,
          () -> service.delete(CERT_ID_1));

      assertEquals("Cannot delete certificate with id: " + CERT_ID_1.id() + " and status: SIGNED",
          exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCertificateIsRevoked() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.REVOKED);
      when(certificate.id()).thenReturn(CERT_ID_1);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(List.of(CERT_ID_1));

      assertThrows(IllegalStateException.class, () -> service.delete(CERT_ID_1));
    }

    @Test
    void shouldNotRemoveWhenExceptionIsThrown() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.SIGNED);
      when(certificate.id()).thenReturn(CERT_ID_1);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(List.of(CERT_ID_1));

      assertThrows(IllegalStateException.class, () -> service.delete(CERT_ID_1));

      verify(certificateRepository).findByIds(List.of(CERT_ID_1));
      verifyNoMoreInteractions(certificateRepository);
    }

    @Test
    void shouldRemoveValidDraft() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.DRAFT);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(List.of(CERT_ID_1));

      service.delete(CERT_ID_1);

      verify(certificateRepository).remove(List.of(CERT_ID_1));
    }

    @Test
    void shouldReturnDeletedCertificate() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.DRAFT);
      final var certificates = List.of(certificate);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(List.of(CERT_ID_1));

      final var deletedCertificates = service.delete(CERT_ID_1);

      assertEquals(certificates, deletedCertificates);
    }

    @Test
    void shouldReturnEmptyListIfNoCertificateFound() {
      doReturn(Collections.emptyList()).when(certificateRepository).findByIds(List.of(CERT_ID_1));

      final var deletedCertificates = service.delete(CERT_ID_1);

      assertEquals(Collections.emptyList(), deletedCertificates);
      verify(certificateRepository).findByIds(List.of(CERT_ID_1));
      verifyNoMoreInteractions(certificateRepository);
    }
  }
}

