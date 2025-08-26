package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
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
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@ExtendWith(MockitoExtension.class)
class SendAnswerDomainServiceTest {


  private static final MessageId MESSAGE_ID = new MessageId("MESSAGE_ID");
  private static final String TEXT = "content";
  private static final Content CONTENT = new Content(TEXT);
  private static final Answer ANSWER = Answer.builder().id(new MessageId("answerId")).build();
  @Mock
  MessageRepository messageRepository;
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  MessageEventDomainService messageEventDomainService;
  @InjectMocks
  SendAnswerDomainService sendAnswerDomainService;

  @Test
  void shallThrowIfNotAllowedToSendAnswer() {
    final var certificate = mock(MedicalCertificate.class);
    final var message = mock(Message.class);

    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.SEND_ANSWER, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class, () -> sendAnswerDomainService.send(
        message, certificate, ACTION_EVALUATION, CONTENT
    ));
  }

  @Test
  void shallReturnSentMessageWithAnswer() {
    final var certificate = mock(MedicalCertificate.class);
    final var message = mock(Message.class);
    final var expectedMessage = mock(Message.class);

    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.SEND_ANSWER, Optional.of(ACTION_EVALUATION));
    doReturn(expectedMessage).when(messageRepository).save(message);
    doReturn(ANSWER).when(expectedMessage).answer();

    final var actualMessage = sendAnswerDomainService.send(message, certificate,
        ACTION_EVALUATION, CONTENT);
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void shallUpdateMessageWithSentAnswer() {
    final var certificate = mock(MedicalCertificate.class);
    final var message = mock(Message.class);

    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.SEND_ANSWER, Optional.of(ACTION_EVALUATION));
    doReturn(message).when(messageRepository).save(message);
    doReturn(ANSWER).when(message).answer();

    sendAnswerDomainService.send(message, certificate, ACTION_EVALUATION, CONTENT);
    verify(message).sendAnswer(Staff.create(ACTION_EVALUATION.user()), CONTENT);
  }

  @Test
  void shallPublishEventToMessageEventDomainService() {
    final var certificate = mock(MedicalCertificate.class);
    final var message = mock(Message.class);
    final var certificateEventCaptor = ArgumentCaptor.forClass(MessageEvent.class);

    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.SEND_ANSWER, Optional.of(ACTION_EVALUATION));
    doReturn(message).when(messageRepository).save(message);
    doReturn(ANSWER).when(message).answer();

    sendAnswerDomainService.send(message, certificate, ACTION_EVALUATION, CONTENT);
    verify(messageEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(MessageEventType.SEND_ANSWER,
            certificateEventCaptor.getValue().type()),
        () -> assertEquals(ANSWER.id(), certificateEventCaptor.getValue().messageId()),
        () -> assertEquals(CERTIFICATE_ID, certificateEventCaptor.getValue().certificateId()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }
}