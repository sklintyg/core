package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@ExtendWith(MockitoExtension.class)
class LockCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @InjectMocks
  private LockCertificateDomainService service;

  private LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1);
  private CertificatesRequest request = CertificatesRequest.builder()
      .createdTo(cutoffDate)
      .statuses(List.of(Status.DRAFT))
      .build();

  @Test
  void shallLockDraft() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(List.of(certificate)).when(certificateRepository).findByCertificatesRequest(request);

    service.lock(cutoffDate);

    verify(certificate).lock();
  }

  @Test
  void shallPersistLockedDraft() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(List.of(certificate)).when(certificateRepository).findByCertificatesRequest(request);

    service.lock(cutoffDate);

    verify(certificateRepository).save(certificate);
  }

  @Test
  void shallReturnLockedDrafts() {
    final var certificate = mock(MedicalCertificate.class);
    final var lockedCertificate = mock(MedicalCertificate.class);

    doReturn(List.of(certificate)).when(certificateRepository).findByCertificatesRequest(request);
    doReturn(lockedCertificate).when(certificateRepository).save(certificate);

    final var lockedCertificates = service.lock(cutoffDate);

    assertEquals(List.of(lockedCertificate), lockedCertificates);
  }
}