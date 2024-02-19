package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@ExtendWith(MockitoExtension.class)
class GetCertificateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private Certificate certificate;
  @InjectMocks
  private GetCertificateDomainService getCertificateDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallValidateIfAllowedToReadCertificate() {
    doReturn(true).when(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
    verify(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
  }

  @Test
  void shallThrowIfNotAllowedToRead() {
    doReturn(false).when(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    assertThrows(CertificateActionForbidden.class,
        () -> getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION)
    );
  }

  @Test
  void shallUpdateCertificateMetadata() {
    doReturn(true).when(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
    verify(certificate).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallReturnCertificate() {
    doReturn(true).when(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    final var actualCertificate = getCertificateDomainService.get(CERTIFICATE_ID,
        ACTION_EVALUATION);
    assertEquals(certificate, actualCertificate);
  }
}
