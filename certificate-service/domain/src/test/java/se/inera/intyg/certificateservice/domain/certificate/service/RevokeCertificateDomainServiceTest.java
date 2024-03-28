package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.REVOKE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class RevokeCertificateDomainServiceTest {

  private static final String REASON = "reason";
  private static final String MESSAGE = "message";
  private static final RevokedInformation REVOKED_INFORMATION = new RevokedInformation(REASON,
      MESSAGE);

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private RevokeCertificateDomainService revokeCertificateDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToRevoke() {
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(REVOKE, ACTION_EVALUATION);

    assertThrows(CertificateActionForbidden.class,
        () -> revokeCertificateDomainService.revoke(CERTIFICATE_ID, ACTION_EVALUATION,
            REVOKED_INFORMATION)
    );
  }

  @Test
  void shallRevokeCertificate() {

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(REVOKE, ACTION_EVALUATION);

    revokeCertificateDomainService.revoke(CERTIFICATE_ID, ACTION_EVALUATION, REVOKED_INFORMATION);

    verify(certificate).revoke(ACTION_EVALUATION, REVOKED_INFORMATION);
  }

  @Test
  void shallReturnRevokedCertificate() {
    final var expectedCertificate = mock(Certificate.class);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(REVOKE, ACTION_EVALUATION);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = revokeCertificateDomainService.revoke(CERTIFICATE_ID,
        ACTION_EVALUATION, REVOKED_INFORMATION);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishRevokedCertificateEvent() {
    final var expectedCertificate = mock(Certificate.class);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(REVOKE, ACTION_EVALUATION);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    revokeCertificateDomainService.revoke(CERTIFICATE_ID, ACTION_EVALUATION, REVOKED_INFORMATION);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.REVOKED, certificateEventCaptor.getValue().type()),
        () -> assertEquals(expectedCertificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }
}
