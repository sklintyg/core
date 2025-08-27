package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.RENEW;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REFERENCE;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class RenewCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private RenewCertificateDomainService renewCertificateDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToRenew() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class,
        () -> renewCertificateDomainService.renew(CERTIFICATE_ID, ACTION_EVALUATION,
            EXTERNAL_REFERENCE)
    );
  }

  @Test
  void shallRenewCertificate() {
    final var newCertificate = mock(MedicalCertificate.class);
    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));
    doReturn(newCertificate).when(certificate).renew(ACTION_EVALUATION);

    renewCertificateDomainService.renew(CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);

    verify(certificate).renew(ACTION_EVALUATION);
  }

  @Test
  void shallSetExternalReferenceOnRenewCertificate() {
    final var newCertificate = mock(MedicalCertificate.class);
    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));
    doReturn(newCertificate).when(certificate).renew(ACTION_EVALUATION);

    renewCertificateDomainService.renew(CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);

    verify(newCertificate).externalReference(EXTERNAL_REFERENCE);
  }

  @Test
  void shallReturnNewCertificate() {
    final var expectedCertificate = mock(MedicalCertificate.class);
    final var newCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));
    doReturn(newCertificate).when(certificate).renew(ACTION_EVALUATION);
    doReturn(expectedCertificate).when(certificateRepository).save(newCertificate);

    final var actualCertificate = renewCertificateDomainService.renew(CERTIFICATE_ID,
        ACTION_EVALUATION, EXTERNAL_REFERENCE);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishRenewCertificateEvent() {
    final var expectedCertificate = mock(MedicalCertificate.class);
    final var newCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));
    doReturn(newCertificate).when(certificate).renew(ACTION_EVALUATION);
    doReturn(expectedCertificate).when(certificateRepository).save(newCertificate);

    renewCertificateDomainService.renew(CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.RENEW, certificateEventCaptor.getValue().type()),
        () -> assertEquals(expectedCertificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var certificate = mock(MedicalCertificate.class);
    final var expectedReason = List.of("expectedReason");
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(RENEW, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> renewCertificateDomainService.renew(CERTIFICATE_ID, ACTION_EVALUATION,
            EXTERNAL_REFERENCE)
    );

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }
}