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
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class LockCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @InjectMocks
  private LockCertificateDomainService service;

  @Test
  void shallLockDraft() {
    final var cutoffDate = LocalDateTime.now();
    final var certificate = mock(Certificate.class);
    doReturn(List.of(certificate)).when(certificateRepository).draftsCreatedBefore(cutoffDate);

    service.lock(cutoffDate);

    verify(certificate).lock();
  }

  @Test
  void shallPersistLockedDraft() {
    final var cutoffDate = LocalDateTime.now();
    final var certificate = mock(Certificate.class);
    doReturn(List.of(certificate)).when(certificateRepository).draftsCreatedBefore(cutoffDate);

    service.lock(cutoffDate);

    verify(certificateRepository).save(certificate);
  }

  @Test
  void shallReturnLockedDrafts() {
    final var cutoffDate = LocalDateTime.now();
    final var certificate = mock(Certificate.class);
    final var lockedCertificate = mock(Certificate.class);

    doReturn(List.of(certificate)).when(certificateRepository).draftsCreatedBefore(cutoffDate);
    doReturn(lockedCertificate).when(certificateRepository).save(certificate);

    final var lockedCertificates = service.lock(cutoffDate);

    assertEquals(List.of(lockedCertificate), lockedCertificates);
  }
}
