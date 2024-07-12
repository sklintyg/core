package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class SetCertificatesToLockedDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @InjectMocks
  private SetCertificatesToLockedDomainService service;

  @Test
  void shallLockDraft() {
    final var certificate = mock(Certificate.class);
    service.lock(List.of(certificate));
    verify(certificate).lock();
  }

  @Test
  void shallPersistLockedDraft() {
    final var certificate = mock(Certificate.class);
    service.lock(List.of(certificate));
    verify(certificateRepository).save(certificate);
  }
}
