package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionAnswerMessage;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionCannotComplement;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionComplement;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionForwardMessage;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionHandleMessage;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@ExtendWith(MockitoExtension.class)
class MessageTest {

  private static final Content CONTENT = new Content("content");
  @Mock
  private Certificate certificate;

  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();

  @Nested
  class ComplementTests {

    private static final CertificateActionComplement CERTIFICATE_ACTION_COMPLEMENT = CertificateActionComplement.builder()
        .certificateActionSpecification(
            CertificateActionSpecification.builder()
                .certificateActionType(CertificateActionType.COMPLEMENT)
                .build()
        )
        .build();

    @Test
    void shallIncludeMessageActionComplement() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_COMPLEMENT)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertTrue(messageActions.contains(MessageActionFactory.complement()));
    }

    @Test
    void shallExcludeMessageActionComplementIfWrongType() {
      final var message = Message.builder()
          .type(MessageType.REMINDER)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_COMPLEMENT)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.complement()));
    }

    @Test
    void shallExcludeMessageActionComplementIfHandled() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.HANDLED)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_COMPLEMENT)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.complement()));
    }

    @Test
    void shallExcludeMessageActionComplementIfCertificateActionComplementIsMissing() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(Collections.emptyList()).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.complement()));
    }
  }

  @Nested
  class CannotComplementTests {

    private static final CertificateActionCannotComplement CERTIFICATE_ACTION_CANNOT_COMPLEMENT =
        CertificateActionCannotComplement.builder()
            .certificateActionSpecification(
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.CANNOT_COMPLEMENT)
                    .build()
            )
            .build();

    @Test
    void shallIncludeMessageActionCannotComplement() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_CANNOT_COMPLEMENT)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertTrue(messageActions.contains(MessageActionFactory.cannotComplement()));
    }

    @Test
    void shallExcludeMessageActionCannotComplementIfWrongType() {
      final var message = Message.builder()
          .type(MessageType.REMINDER)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_CANNOT_COMPLEMENT)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.cannotComplement()));
    }

    @Test
    void shallExcludeMessageActionCannotComplementIfHandled() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.HANDLED)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_CANNOT_COMPLEMENT)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.cannotComplement()));
    }

    @Test
    void shallExcludeMessageActionCannotComplementIfCertificateActionComplementIsMissing() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(Collections.emptyList()).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.cannotComplement()));
    }
  }

  @Nested
  class HandleComplementTests {

    private static final CertificateActionCannotComplement CERTIFICATE_ACTION_HANDLE_COMPLEMENT =
        CertificateActionCannotComplement.builder()
            .certificateActionSpecification(
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.HANDLE_COMPLEMENT)
                    .build()
            )
            .build();

    @Test
    void shallIncludeMessageActionCannotComplement() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_HANDLE_COMPLEMENT)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertTrue(messageActions.contains(MessageActionFactory.handleComplement()));
    }

    @Test
    void shallExcludeMessageActionCannotComplementIfWrongType() {
      final var message = Message.builder()
          .type(MessageType.REMINDER)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_HANDLE_COMPLEMENT)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.handleComplement()));
    }

    @Test
    void shallExcludeMessageActionCannotComplementIfHandled() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.HANDLED)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_HANDLE_COMPLEMENT)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.handleComplement()));
    }

    @Test
    void shallExcludeMessageActionCannotComplementIfCertificateActionComplementIsMissing() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(Collections.emptyList()).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.handleComplement()));
    }
  }

  @Nested
  class ForwardMessageTests {

    private static final CertificateActionForwardMessage CERTIFICATE_ACTION_FORWARD =
        CertificateActionForwardMessage.builder()
            .certificateActionSpecification(
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
                    .build()
            )
            .build();

    @Test
    void shallIncludeMessageActionForward() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_FORWARD)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertTrue(messageActions.contains(MessageActionFactory.forward()));
    }

    @Test
    void shallExcludeMessageActionForwardIfHandled() {
      final var message = Message.builder()
          .type(MessageType.REMINDER)
          .status(MessageStatus.HANDLED)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_FORWARD)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.forward()));
    }

    @Test
    void shallExcludeMessageActionForwardIfCertificateActionComplementIsMissing() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(Collections.emptyList()).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.forward()));
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
  class HandleAdministrativeMessage {

    private static final CertificateActionHandleMessage CERTIFICATE_ACTION_HANDLE_MESSAGE =
        CertificateActionHandleMessage.builder()
            .certificateActionSpecification(
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.HANDLE_MESSAGE)
                    .build()
            )
            .build();

    @Test
    void shallIncludeMessageActionHandleMessageWhenActionAvailable() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_HANDLE_MESSAGE)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertTrue(messageActions.contains(MessageActionFactory.handleMessage()));
    }

    @Test
    void shallExcludeMessageActionHandleMessageWhenActionNotAvailable() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of()).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.handleMessage()));
    }

    @Test
    void shallExcludeMessageActionHandleMessageWhenNotAdministrativeMessage() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_HANDLE_MESSAGE)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.handleMessage()));
    }
  }

  @Nested
  class AnswerMessageTests {

    private static final CertificateActionAnswerMessage CERTIFICATE_ACTION_ANSWER =
        CertificateActionAnswerMessage.builder()
            .certificateActionSpecification(
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.ANSWER_MESSAGE)
                    .build()
            )
            .build();

    @Test
    void shallIncludeMessageActionAnswer() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_ANSWER)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertTrue(messageActions.contains(MessageActionFactory.answer()));
    }

    @Test
    void shallExcludeMessageActionAnswerIfAuthoredStaffIsNotNull() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .authoredStaff(AJLA_DOKTOR)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_ANSWER)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.answer()));
    }

    @Test
    void shallExcludeMessageActionAnswerIfTypeIsComplement() {
      final var message = Message.builder()
          .type(MessageType.COMPLEMENT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_ANSWER)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.answer()));
    }

    @Test
    void shallExcludeMessageActionAnswer() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.SENT)
          .build();

      doReturn(Collections.emptyList()).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.answer()));
    }

    @Test
    void shallExcludeMessageActionAnswerIfAnswerAlreadyExists() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.SENT)
          .answer(Answer.builder().build())
          .build();

      doReturn(List.of(CERTIFICATE_ACTION_ANSWER)).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.answer()));
    }

    @Test
    void shallExcludeMessageActionAnswerIfMessageIsHandled() {
      final var message = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.HANDLED)
          .answer(Answer.builder().build())
          .build();

      doReturn(Collections.emptyList()).when(certificate).actionsInclude(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.answer()));
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
      assertEquals(MessageStatus.SENT, message.answer().status());
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
}
