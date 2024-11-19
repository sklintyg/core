package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.message.model.MessageAction;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@ExtendWith(MockitoExtension.class)
class MessageTest {

  private static final Content CONTENT = new Content("content");
  @Mock
  private Certificate certificate;
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();

  @Nested
  class ActionsTests {

    @Test
    void shallReturnListOfMessageActionLinks() {
      final var messageActionLink = MessageActionLink.builder().build();
      final var expectedResult = List.of(messageActionLink);
      final var message = Message.builder().build();

      final var certificateModel = mock(CertificateModel.class);
      final var certificateAction = mock(CertificateAction.class);
      final var messageAction = mock(MessageAction.class);
      doReturn(List.of(certificateAction)).when(certificate)
          .actionsInclude(Optional.of(ACTION_EVALUATION));
      doReturn(certificateModel).when(certificate).certificateModel();
      doReturn(List.of(messageAction)).when(certificateModel).messageActions();
      doReturn(true).when(messageAction).evaluate(List.of(certificateAction), message);
      doReturn(messageActionLink).when(messageAction).actionLink();

      final var actualResult = message.actions(ACTION_EVALUATION, certificate);
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallFilterListOfMessageActionLinksIfEvaluateReturnsFalse() {
      final var messageActionLink = MessageActionLink.builder().build();
      final var expectedResult = List.of(messageActionLink);
      final var message = Message.builder().build();

      final var certificateModel = mock(CertificateModel.class);
      final var certificateAction = mock(CertificateAction.class);
      final var messageActionValid = mock(MessageAction.class);
      final var messageActionFalse = mock(MessageAction.class);
      doReturn(List.of(certificateAction)).when(certificate)
          .actionsInclude(Optional.of(ACTION_EVALUATION));
      doReturn(certificateModel).when(certificate).certificateModel();
      doReturn(List.of(messageActionValid, messageActionFalse)).when(certificateModel)
          .messageActions();
      doReturn(true).when(messageActionValid).evaluate(List.of(certificateAction), message);
      doReturn(false).when(messageActionFalse).evaluate(List.of(certificateAction), message);
      doReturn(messageActionLink).when(messageActionValid).actionLink();

      final var actualResult = message.actions(ACTION_EVALUATION, certificate);
      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class TestHandle {

    @Test
    void shallSetStatusToHandled() {
      final var unhandledMessage = complementMessageBuilder().build();
      unhandledMessage.handle();
      assertEquals(MessageStatus.HANDLED, unhandledMessage.status());
    }

    @Test
    void shallUpdateModified() {
      final var unhandledMessage = complementMessageBuilder().build();
      final var modifiedBefore = unhandledMessage.modified();
      unhandledMessage.handle();
      assertAll(
          () -> assertNotNull(unhandledMessage.modified()),
          () -> assertNotEquals(modifiedBefore, unhandledMessage.modified())
      );
    }

    @Test
    void shallNotUpdateModifiedIfAlreadyHandled() {
      final var unhandledMessage = complementMessageBuilder()
          .status(MessageStatus.HANDLED)
          .build();

      final var modifiedBefore = unhandledMessage.modified();
      unhandledMessage.handle();
      assertEquals(modifiedBefore, unhandledMessage.modified());
    }
  }

  @Nested
  class AnswerTests {

    @Test
    void shallUpdateAnswer() {
      final var answer = Answer.builder()
          .build();
      final var message = Message.builder().build();

      message.answer(answer);
      assertEquals(answer, message.answer());
    }
  }

  @Nested
  class CreateMessageTests {

    private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
    private static final Content CONTENT = new Content("content");

    @Test
    void shallIncludeId() {
      assertNotNull(Message.create(MessageType.CONTACT, CONTENT, CERTIFICATE_ID, AJLA_DOKTOR).id());
    }

    @Test
    void shallIncludeType() {
      assertEquals(MessageType.CONTACT,
          Message.create(MessageType.CONTACT, CONTENT, CERTIFICATE_ID, AJLA_DOKTOR).type());
    }

    @Test
    void shallIncludeContent() {
      assertEquals(CONTENT,
          Message.create(MessageType.CONTACT, CONTENT, CERTIFICATE_ID, AJLA_DOKTOR).content());
    }

    @Test
    void shallIncludeStatus() {
      assertEquals(MessageStatus.DRAFT,
          Message.create(MessageType.CONTACT, CONTENT, CERTIFICATE_ID, AJLA_DOKTOR).status());
    }

    @Test
    void shallIncludeAuthor() {
      assertEquals(AJLA_DOKTOR.name().fullName(),
          Message.create(MessageType.CONTACT, CONTENT, CERTIFICATE_ID, AJLA_DOKTOR).author()
              .author());
    }

    @Test
    void shallIncludeAuthoredStaff() {
      assertEquals(AJLA_DOKTOR,
          Message.create(MessageType.CONTACT, CONTENT, CERTIFICATE_ID, AJLA_DOKTOR)
              .authoredStaff());
    }

    @Test
    void shallIncludeForwarded() {
      assertEquals(new Forwarded(false),
          Message.create(MessageType.CONTACT, CONTENT, CERTIFICATE_ID, AJLA_DOKTOR).forwarded());
    }

    @Test
    void shallIncludeCertificateId() {
      assertEquals(CERTIFICATE_ID,
          Message.create(MessageType.CONTACT, CONTENT, CERTIFICATE_ID, AJLA_DOKTOR)
              .certificateId());
    }
  }

  @Nested
  class UpdateTests {

    @Test
    void shallUpdateContent() {
      final var expectedContent = new Content("newContent");
      final var message = Message.builder().build();
      final var staff = Staff.builder().build();
      final var subject = new Subject("subject");
      message.update(expectedContent, MessageType.CONTACT, staff, subject);

      assertEquals(expectedContent, message.content());
    }

    @Test
    void shallUpdateType() {
      final var expectedType = MessageType.CONTACT;
      final var expectedContent = new Content("newContent");
      final var message = Message.builder().build();
      final var staff = Staff.builder().build();
      final var subject = new Subject("subject");
      message.update(expectedContent, expectedType, staff, subject);

      assertEquals(expectedType, message.type());
    }

    @Test
    void shallUpdateAuthoredStaff() {
      final var expectedContent = new Content("newContent");
      final var message = Message.builder().build();
      final var staff = Staff.builder().build();
      final var subject = new Subject("subject");
      message.update(expectedContent, MessageType.CONTACT, staff, subject);

      assertEquals(staff, message.authoredStaff());
    }

    @Test
    void shallUpdateSubject() {
      final var expectedContent = new Content("newContent");
      final var message = Message.builder().build();
      final var staff = Staff.builder().build();
      final var subject = new Subject("subject");
      message.update(expectedContent, MessageType.CONTACT, staff, subject);

      assertEquals(subject, message.subject());
    }
  }

  @Nested
  class SendTests {

    @Test
    void shallSetStatusSent() {
      final var message = Message.builder().build();
      message.send();
      assertEquals(MessageStatus.SENT, message.status());
    }

    @Test
    void shallSetSent() {
      final var message = Message.builder().build();
      message.send();
      assertNotNull(message.sent());
    }
  }

  @Nested
  class DeleteTests {

    @Test
    void shallThrowIfMessageIsNotStatusDraft() {
      final var message = Message.builder()
          .status(MessageStatus.SENT)
          .build();

      assertThrows(IllegalStateException.class, message::delete);
    }

    @Test
    void shallUpdateStatusToDeletedDraftIfDraft() {
      final var message = Message.builder()
          .status(MessageStatus.DRAFT)
          .build();

      message.delete();

      assertEquals(MessageStatus.DELETED_DRAFT, message.status());
    }
  }

  @Nested
  class SaveAnswerTests {

    @Nested
    class AnswerIsNull {

      @Test
      void shallIncludeNewlyGeneratedIdIfAnswerIsNull() {
        final var message = Message.builder().build();
        message.saveAnswer(AJLA_DOKTOR, CONTENT);
        assertNotNull(message.answer().id());
      }

      @Test
      void shallIncludeSubject() {
        final var expectedSubject = new Subject("expectedSubject");
        final var message = Message.builder()
            .subject(expectedSubject)
            .build();
        message.saveAnswer(AJLA_DOKTOR, CONTENT);
        assertEquals(expectedSubject, message.answer().subject());
      }

      @Test
      void shallIncludeContent() {
        final var expectedContent = new Content("expectedContent");
        final var message = Message.builder()
            .content(expectedContent)
            .build();
        message.saveAnswer(AJLA_DOKTOR, expectedContent);
        assertEquals(expectedContent, message.answer().content());
      }

      @Test
      void shallIncludeStatus() {
        final var message = Message.builder().build();
        message.saveAnswer(AJLA_DOKTOR, CONTENT);
        assertEquals(MessageStatus.DRAFT, message.answer().status());
      }

      @Test
      void shallIncludeAuthor() {
        final var message = Message.builder().build();
        message.saveAnswer(AJLA_DOKTOR, CONTENT);
        assertEquals(AJLA_DOKTOR.name().fullName(), message.answer().author().author());
      }

      @Test
      void shallIncludeAuthoredStaff() {
        final var message = Message.builder().build();
        message.saveAnswer(AJLA_DOKTOR, CONTENT);
        assertEquals(AJLA_DOKTOR, message.answer().authoredStaff());
      }

    }

    @Nested
    class AnswerIsNotNull {

      @Test
      void shallUpdateContent() {
        final var message = Message.builder()
            .answer(Answer.builder().build())
            .build();
        message.saveAnswer(AJLA_DOKTOR, CONTENT);
        assertEquals(CONTENT, message.answer().content());
      }

      @Test
      void shallUpdateAuthoredStaff() {
        final var message = Message.builder()
            .answer(Answer.builder().build())
            .build();
        message.saveAnswer(AJLA_DOKTOR, CONTENT);
        assertEquals(AJLA_DOKTOR, message.answer().authoredStaff());
      }

      @Test
      void shallUpdateAuthor() {
        final var message = Message.builder()
            .answer(Answer.builder().build())
            .build();
        message.saveAnswer(AJLA_DOKTOR, CONTENT);
        assertEquals(AJLA_DOKTOR.name().fullName(), message.answer().author().author());
      }
    }
  }

  @Nested
  class DeleteAnswerTests {

    @Test
    void shallThrowIfAnswerIsNull() {
      final var message = Message.builder().build();
      assertThrows(IllegalStateException.class, message::deleteAnswer);
    }

    @Test
    void shallThrowIfAnswerStatusIsNotDraft() {
      final var answer = Answer.builder()
          .status(MessageStatus.SENT)
          .build();

      final var message = Message.builder()
          .answer(answer)
          .build();

      assertThrows(IllegalStateException.class, message::deleteAnswer);
    }

    @Test
    void shallUpdateStatusToDeletedDraftOnAnswer() {
      final var answer = Answer.builder()
          .status(MessageStatus.DRAFT)
          .build();

      final var message = Message.builder()
          .answer(answer)
          .build();

      message.deleteAnswer();

      assertEquals(MessageStatus.DELETED_DRAFT, message.answer().status());
    }
  }

  @Nested
  class SendAnswer {

    @Test
    void shallThrowIfIncorrectType() {
      final var message = Message.builder()
          .type(MessageType.REMINDER)
          .build();

      assertThrows(IllegalStateException.class, () -> message.sendAnswer(AJLA_DOKTOR, CONTENT));
    }

    @Test
    void shallUpdateStatus() {
      final var answer = Answer.builder()
          .build();
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .answer(answer)
          .build();

      message.sendAnswer(AJLA_DOKTOR, CONTENT);
      assertEquals(MessageStatus.HANDLED, message.answer().status());
    }

    @Test
    void shallUpdateContent() {
      final var answer = Answer.builder()
          .build();
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .answer(answer)
          .build();

      message.sendAnswer(AJLA_DOKTOR, CONTENT);
      assertEquals(CONTENT, message.answer().content());
    }

    @Test
    void shallUpdateAuthoredStaff() {
      final var answer = Answer.builder()
          .build();
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .answer(answer)
          .build();

      message.sendAnswer(AJLA_DOKTOR, CONTENT);
      assertEquals(AJLA_DOKTOR, message.answer().authoredStaff());
    }

    @Test
    void shallUpdateSent() {
      final var answer = Answer.builder()
          .build();
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .answer(answer)
          .build();

      message.sendAnswer(AJLA_DOKTOR, CONTENT);
      assertNotNull(message.answer().sent());
    }

    @Test
    void shallSetMessageAsHandled() {
      final var answer = Answer.builder()
          .build();
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .answer(answer)
          .build();

      message.sendAnswer(AJLA_DOKTOR, CONTENT);
      assertEquals(MessageStatus.HANDLED, message.status());
    }
  }

  @Nested
  class TypeTests {

    @Test
    void shallReturnTrueIfStatusMatch() {
      final var messageTypes = List.of(MessageType.COMPLEMENT);
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .build();

      assertTrue(message.type(messageTypes));
    }

    @Test
    void shallReturnFalseIfStatusDontMatch() {
      final var messageTypes = List.of(MessageType.REMINDER);
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .build();

      assertFalse(message.type(messageTypes));
    }
  }


  @Nested
  class SentTests {

    @Test
    void shallReturnTrueIfStatusIsSent() {
      final var message = Message.builder()
          .status(MessageStatus.SENT)
          .build();
      assertTrue(message.isSent());
    }

    @Test
    void shallReturnFalseIfStatusIsNotSent() {
      final var message = Message.builder()
          .status(MessageStatus.DRAFT)
          .build();
      assertFalse(message.isSent());
    }
  }

  @Nested
  class ForwardTests {

    @Test
    void shallSetForwardedTrueIfSent() {
      final var message = Message.builder()
          .forwarded(new Forwarded(false))
          .status(MessageStatus.SENT)
          .build();

      message.forward();

      assertTrue(message.forwarded().value());
    }

    @Test
    void shallNotSetForwardedTrueIfNotSent() {
      final var message = Message.builder()
          .forwarded(new Forwarded(false))
          .status(MessageStatus.DRAFT)
          .build();

      message.forward();

      assertFalse(message.forwarded().value());
    }
  }

  @Nested
  class HasAnswerTests {

    @Test
    void shallReturnTrueIfMessageHasAnswer() {
      final var messageWithAnswer = Message.builder()
          .answer(
              Answer.builder().build()
          )
          .build();

      assertTrue(messageWithAnswer.hasAnswer());
    }

    @Test
    void shallReturnFalseIfMessageDontHaveAnswer() {
      final var messageWithAnswer = Message.builder()
          .build();

      assertFalse(messageWithAnswer.hasAnswer());
    }
  }

  @Nested
  class HasAuthoredStaffTests {

    @Test
    void shallReturnTrueIfMessageHasAuthoredStaff() {
      final var messageWithAnswer = Message.builder()
          .authoredStaff(
              Staff.builder().build()
          )
          .build();

      assertTrue(messageWithAnswer.hasAuthoredStaff());
    }

    @Test
    void shallReturnFalseIfMessageDontHaveAuthoredStaff() {
      final var messageWithAnswer = Message.builder()
          .build();

      assertFalse(messageWithAnswer.hasAuthoredStaff());
    }
  }
}
