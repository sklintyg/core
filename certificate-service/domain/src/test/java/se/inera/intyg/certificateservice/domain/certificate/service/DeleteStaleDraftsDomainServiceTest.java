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
          .statuses(Status.unsigned())
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
          .statuses(Status.unsigned())
          .build();

      doReturn(Collections.emptyList()).when(certificateRepository)
          .findByCertificatesRequest(expectedRequest);

      final var result = service.list(cutoffDate);

      assertEquals(Collections.emptyList(), result);
    }
  }

  @Nested
  class DeleteByIdsTests {

    private static final CertificateId CERT_ID_1 = new CertificateId("cert-123");
    private static final CertificateId CERT_ID_2 = new CertificateId("cert-456");

    @Test
    void shouldFindCertificatesByIds() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.DRAFT);
      when(certificate.id()).thenReturn(CERT_ID_1);
      final var certificateIds = List.of(CERT_ID_1);
      final var expectedIds = List.of(CERT_ID_1);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(expectedIds);

      service.delete(certificateIds);

      verify(certificateRepository).findByIds(expectedIds);
    }

    @Test
    void shouldValidateAllCertificatesHaveValidStatus() {
      final var certificate1 = mock(MedicalCertificate.class);
      when(certificate1.status()).thenReturn(Status.DRAFT);
      when(certificate1.id()).thenReturn(CERT_ID_1);

      final var certificate2 = mock(MedicalCertificate.class);
      when(certificate2.status()).thenReturn(Status.LOCKED_DRAFT);
      when(certificate2.id()).thenReturn(CERT_ID_2);

      final var certificateIds = List.of(CERT_ID_1, CERT_ID_2);
      final var expectedIds = List.of(CERT_ID_1, CERT_ID_2);

      doReturn(List.of(certificate1, certificate2)).when(certificateRepository)
          .findByIds(expectedIds);

      service.delete(certificateIds);

      verify(certificateRepository).remove(expectedIds);
    }

    @Test
    void shouldThrowExceptionWhenCertificateHasInvalidStatus() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.SIGNED);
      when(certificate.id()).thenReturn(CERT_ID_1);
      final var certificateIds = List.of(CERT_ID_1);
      final var expectedIds = List.of(CERT_ID_1);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(expectedIds);

      final var exception = assertThrows(IllegalStateException.class,
          () -> service.delete(certificateIds));

      assertEquals("Cannot delete certificate with id: " + CERT_ID_1 + " and status: SIGNED",
          exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCertificateIsRevoked() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.REVOKED);
      when(certificate.id()).thenReturn(CERT_ID_1);
      final var certificateIds = List.of(CERT_ID_1);
      final var expectedIds = List.of(CERT_ID_1);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(expectedIds);

      assertThrows(IllegalStateException.class, () -> service.delete(certificateIds));
    }

    @Test
    void shouldNotRemoveWhenExceptionIsThrown() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.SIGNED);
      when(certificate.id()).thenReturn(CERT_ID_1);
      final var certificateIds = List.of(CERT_ID_1);
      final var expectedIds = List.of(CERT_ID_1);

      doReturn(List.of(certificate)).when(certificateRepository).findByIds(expectedIds);

      assertThrows(IllegalStateException.class, () -> service.delete(certificateIds));

      verify(certificateRepository).findByIds(expectedIds);
      verifyNoMoreInteractions(certificateRepository);
    }

    @Test
    void shouldRemoveAllValidDrafts() {
      final var certificate1 = mock(MedicalCertificate.class);
      when(certificate1.status()).thenReturn(Status.DRAFT);
      when(certificate1.id()).thenReturn(CERT_ID_1);

      final var certificate2 = mock(MedicalCertificate.class);
      when(certificate2.status()).thenReturn(Status.DELETED_DRAFT);
      when(certificate2.id()).thenReturn(CERT_ID_2);

      final var certificateIds = List.of(CERT_ID_1, CERT_ID_2);
      final var expectedIds = List.of(CERT_ID_1, CERT_ID_2);

      doReturn(List.of(certificate1, certificate2)).when(certificateRepository)
          .findByIds(expectedIds);

      service.delete(certificateIds);

      verify(certificateRepository).remove(expectedIds);
    }

    @Test
    void shouldReturnDeletedCertificates() {
      final var certificate = mock(MedicalCertificate.class);
      when(certificate.status()).thenReturn(Status.DRAFT);
      when(certificate.id()).thenReturn(CERT_ID_1);
      final var certificates = List.of(certificate);
      final var certificateIds = List.of(CERT_ID_1);
      final var expectedIds = List.of(CERT_ID_1);

      doReturn(certificates).when(certificateRepository).findByIds(expectedIds);

      final var deletedCertificates = service.delete(certificateIds);

      assertEquals(certificates, deletedCertificates);
    }

    @Test
    void shouldNotRemoveIfNoCertificatesFound() {
      final var certificateIds = List.of(CERT_ID_1);
      final var expectedIds = List.of(CERT_ID_1);

      doReturn(Collections.emptyList()).when(certificateRepository).findByIds(expectedIds);

      service.delete(certificateIds);

      verify(certificateRepository).findByIds(expectedIds);
      verifyNoMoreInteractions(certificateRepository);
    }
  }
}

