package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.CANNOT_COMPLEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;

import java.time.LocalDateTime;
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
import se.inera.intyg.certificateservice.domain.event.model.MessageEvent;
import se.inera.intyg.certificateservice.domain.event.model.MessageEventType;
import se.inera.intyg.certificateservice.domain.event.service.MessageEventDomainService;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.service.SetMessagesToHandleDomainService;

@ExtendWith(MockitoExtension.class)
class AnswerComplementDomainServiceTest {

  private static final List<Message> MESSAGES = List.of(
      Message.builder()
          .type(MessageType.COMPLEMENT)
          .answer(
              Answer.builder().build()
          )
          .build()
  );
  private static final Content CONTENT = new Content("content");
  private static final MessageId MESSAGE_ID = new MessageId("messageId");
  @Mock
  private SetMessagesToHandleDomainService setMessagesToHandleDomainService;
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private MessageEventDomainService messageEventDomainService;
  @InjectMocks
  private AnswerComplementDomainService answerComplementDomainService;

  @Test
  void shallThrowExceptionIfUserDontHasNoAccessToCannotComplement() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(CANNOT_COMPLEMENT, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class,
        () -> answerComplementDomainService.answer(CERTIFICATE_ID, ACTION_EVALUATION,
            CONTENT)
    );
  }

  @Test
  void shallAnswerComplementForCertificate() {
    final var certificate = mock(MedicalCertificate.class);

    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(CANNOT_COMPLEMENT, Optional.of(ACTION_EVALUATION));
    doReturn(MESSAGES).when(certificate).messages(MessageType.COMPLEMENT);

    answerComplementDomainService.answer(CERTIFICATE_ID, ACTION_EVALUATION,
        CONTENT);

    verify(certificate).answerComplement(ACTION_EVALUATION, CONTENT);
  }

  @Test
  void shallReturnCertificate() {
    final var expectedCertificate = mock(MedicalCertificate.class);

    doReturn(expectedCertificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(expectedCertificate)
        .allowTo(CANNOT_COMPLEMENT, Optional.of(ACTION_EVALUATION));
    doReturn(MESSAGES).when(expectedCertificate).messages(MessageType.COMPLEMENT);

    final var actualCertificate = answerComplementDomainService.answer(CERTIFICATE_ID,
        ACTION_EVALUATION, CONTENT);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishAnswerComplementMessageEventWithLatestAnswer() {
    final var certificateEventCaptor = ArgumentCaptor.forClass(MessageEvent.class);
    final var certificate = mock(MedicalCertificate.class);

    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(true).when(certificate).allowTo(CANNOT_COMPLEMENT, Optional.of(ACTION_EVALUATION));

    final var answer = Answer.builder()
        .id(MESSAGE_ID)
        .build();
    doReturn(List.of(
        Message.builder()
            .created(LocalDateTime.now())
            .answer(
                answer
            )
            .build(),
        Message.builder()
            .created(LocalDateTime.now().minusDays(1))
            .answer(
                Answer.builder().build()
            )
            .build()
    )).when(certificate).messages(MessageType.COMPLEMENT);

    answerComplementDomainService.answer(CERTIFICATE_ID, ACTION_EVALUATION,
        CONTENT);

    verify(messageEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(MessageEventType.ANSWER_COMPLEMENT,
            certificateEventCaptor.getValue().type()),
        () -> assertEquals(MESSAGE_ID, certificateEventCaptor.getValue().messageId()),
        () -> assertEquals(CERTIFICATE_ID, certificateEventCaptor.getValue().certificateId()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var certificate = mock(MedicalCertificate.class);
    final var expectedReason = List.of("expectedReason");

    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(CANNOT_COMPLEMENT, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(CANNOT_COMPLEMENT, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> answerComplementDomainService.answer(CERTIFICATE_ID, ACTION_EVALUATION,
            CONTENT)
    );

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }

  @Test
  void shallSetMessagesToHandleIfMessageTypeComplement() {
    final var certificate = mock(MedicalCertificate.class);

    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(CANNOT_COMPLEMENT, Optional.of(ACTION_EVALUATION));
    doReturn(MESSAGES).when(certificate).messages(MessageType.COMPLEMENT);

    answerComplementDomainService.answer(CERTIFICATE_ID, ACTION_EVALUATION,
        CONTENT);

    final ArgumentCaptor<List<Message>> messagesCaptor = ArgumentCaptor.forClass(List.class);
    verify(setMessagesToHandleDomainService).handle(messagesCaptor.capture());

    assertEquals(MESSAGES, messagesCaptor.getValue());
  }
}