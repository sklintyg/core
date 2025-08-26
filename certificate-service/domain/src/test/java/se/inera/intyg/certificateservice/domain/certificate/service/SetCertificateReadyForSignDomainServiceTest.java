package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.READY_FOR_SIGN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;

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
class SetCertificateReadyForSignDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private SetCertificateReadyForSignDomainService setCertificateReadyForSignDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToReady_For_Sign() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(READY_FOR_SIGN, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class,
        () -> setCertificateReadyForSignDomainService.readyForSign(CERTIFICATE_ID,
            ACTION_EVALUATION)
    );
  }

  @Test
  void shallSetCertificateReadyForSign() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(READY_FOR_SIGN, Optional.of(ACTION_EVALUATION));

    setCertificateReadyForSignDomainService.readyForSign(CERTIFICATE_ID, ACTION_EVALUATION);

    verify(certificate).readyForSign(ACTION_EVALUATION);
  }

  @Test
  void shallReturnSentCertificate() {
    final var expectedCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(READY_FOR_SIGN, Optional.of(ACTION_EVALUATION));
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = setCertificateReadyForSignDomainService.readyForSign(
        CERTIFICATE_ID,
        ACTION_EVALUATION);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishReadyForSignCertificateEvent() {
    final var expectedCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(READY_FOR_SIGN, Optional.of(ACTION_EVALUATION));
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    setCertificateReadyForSignDomainService.readyForSign(CERTIFICATE_ID, ACTION_EVALUATION);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.READY_FOR_SIGN,
            certificateEventCaptor.getValue().type()),
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
    doReturn(false).when(certificate).allowTo(READY_FOR_SIGN, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(READY_FOR_SIGN, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> setCertificateReadyForSignDomainService.readyForSign(CERTIFICATE_ID,
            ACTION_EVALUATION));

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }
}