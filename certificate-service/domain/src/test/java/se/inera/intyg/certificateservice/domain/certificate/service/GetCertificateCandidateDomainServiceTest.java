package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.READ;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@ExtendWith(MockitoExtension.class)
class GetCertificateCandidateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private Certificate certificate;
  @Mock
  private Certificate candidateCertificate;
  @InjectMocks
  private GetCertificateCandidateDomainService getCertificateCandidateDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallThrowIfNotAllowedToReadCertificate() {
    doReturn(false).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    assertThrows(CertificateActionForbidden.class,
        () -> getCertificateCandidateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION)
    );
  }

  @Test
  void shallThrowIfNotAllowedToReadCandidateCertificate() {
    doReturn(Optional.of(candidateCertificate)).when(certificate).candidateForUpdate();
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(false).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    assertThrows(CertificateActionForbidden.class,
        () -> getCertificateCandidateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION)
    );
  }

  @Test
  void shallReturnCandidateForUpdate() {
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(true).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(Optional.of(candidateCertificate)).when(certificate).candidateForUpdate();
    final var actualCertificate = getCertificateCandidateDomainService.get(CERTIFICATE_ID,
        ACTION_EVALUATION);
    assertEquals(Optional.of(candidateCertificate), actualCertificate);
  }
}
