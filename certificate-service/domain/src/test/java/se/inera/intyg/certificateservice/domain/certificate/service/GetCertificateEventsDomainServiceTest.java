package se.inera.intyg.certificateservice.domain.certificate.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class GetCertificateEventsDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;

  @InjectMocks
  GetCertificateEventsDomainService getCertificateEventsDomainService;

}