package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.REVOKE;
import static se.inera.intyg.certificateservice.domain.certificate.model.RelationType.COMPLEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.service.SetMessagesToHandleDomainService;
import se.inera.intyg.certificateservice.domain.message.service.SetMessagesToUnhandledDomainService;

@ExtendWith(MockitoExtension.class)
class RevokeCertificateDomainServiceTest {

  private static final String REASON = "reason";
  private static final String MESSAGE = "message";
  private static final RevokedInformation REVOKED_INFORMATION = new RevokedInformation(REASON,
      MESSAGE);

  @Mock
  private SetMessagesToUnhandledDomainService setMessagesToUnhandledDomainService;
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @Mock
  private SetMessagesToHandleDomainService setMessagesToHandleDomainService;
  @InjectMocks
  private RevokeCertificateDomainService revokeCertificateDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToRevoke() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(REVOKE, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class,
        () -> revokeCertificateDomainService.revoke(CERTIFICATE_ID, ACTION_EVALUATION,
            REVOKED_INFORMATION)
    );
  }

  @Test
  void shallRevokeCertificate() {

    final var certificate = mock(MedicalCertificate.class);
    final var revokedCertificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(REVOKE, Optional.of(ACTION_EVALUATION));
    doReturn(revokedCertificate).when(certificateRepository).save(certificate);

    revokeCertificateDomainService.revoke(CERTIFICATE_ID, ACTION_EVALUATION, REVOKED_INFORMATION);

    verify(certificate).revoke(ACTION_EVALUATION, REVOKED_INFORMATION);
  }

  @Test
  void shallReturnRevokedCertificate() {
    final var expectedCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(REVOKE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = revokeCertificateDomainService.revoke(CERTIFICATE_ID,
        ACTION_EVALUATION, REVOKED_INFORMATION);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishRevokedCertificateEvent() {
    final var expectedCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(REVOKE, Optional.of(ACTION_EVALUATION));
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

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var certificate = mock(MedicalCertificate.class);
    final var expectedReason = List.of("expectedReason");
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.REVOKE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(CertificateActionType.REVOKE, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> revokeCertificateDomainService.revoke(CERTIFICATE_ID, ACTION_EVALUATION,
            REVOKED_INFORMATION));

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }

  @Test
  void shallSetMessagesToHandleIfContainingMessages() {
    final var expectedMessages = List.of(COMPLEMENT_MESSAGE);
    final var revokedCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(REVOKE, Optional.of(ACTION_EVALUATION));
    doReturn(revokedCertificate).when(certificateRepository).save(certificate);
    doReturn(expectedMessages).when(revokedCertificate).messages();

    revokeCertificateDomainService.revoke(CERTIFICATE_ID, ACTION_EVALUATION, REVOKED_INFORMATION);

    final ArgumentCaptor<List<Message>> messagesCaptor = ArgumentCaptor.forClass(List.class);
    verify(setMessagesToHandleDomainService).handle(messagesCaptor.capture());

    assertEquals(expectedMessages, messagesCaptor.getValue());
  }

  @Test
  void shallUnhandleMessagesOnParentIfRevokedCertificateHasParentWithComplementRelation() {
    final var complementMessage = Message.builder()
        .type(MessageType.COMPLEMENT)
        .status(MessageStatus.HANDLED)
        .build();

    final var parentCertificate = Relation.builder()
        .certificate(
            MedicalCertificate.builder()
                .messages(
                    List.of(
                        complementMessage,
                        Message.builder()
                            .type(MessageType.ANSWER)
                            .status(MessageStatus.HANDLED)
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var revokedCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(REVOKE, Optional.of(ACTION_EVALUATION));
    doReturn(revokedCertificate).when(certificateRepository).save(certificate);
    doReturn(true).when(revokedCertificate).hasParent(COMPLEMENT);
    doReturn(parentCertificate).when(revokedCertificate).parent();

    revokeCertificateDomainService.revoke(CERTIFICATE_ID, ACTION_EVALUATION, REVOKED_INFORMATION);

    verify(setMessagesToUnhandledDomainService).unhandled(List.of(complementMessage));
  }
}