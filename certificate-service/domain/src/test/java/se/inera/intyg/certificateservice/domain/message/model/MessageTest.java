package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@ExtendWith(MockitoExtension.class)
class MessageTest {

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
  }

  @Nested
  class CreateMessageTests {

    private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
    private static final Content CONTENT = new Content("content");
    private static final Author AUTHOR = new Author("author");

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
}
