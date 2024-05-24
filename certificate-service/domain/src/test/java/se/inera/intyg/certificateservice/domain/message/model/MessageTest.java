package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionCannotComplement;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionComplement;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionForwardMessage;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

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

      doReturn(List.of(CERTIFICATE_ACTION_COMPLEMENT)).when(certificate).actions(
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

      doReturn(List.of(CERTIFICATE_ACTION_COMPLEMENT)).when(certificate).actions(
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

      doReturn(List.of(CERTIFICATE_ACTION_COMPLEMENT)).when(certificate).actions(
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

      doReturn(Collections.emptyList()).when(certificate).actions(
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

      doReturn(List.of(CERTIFICATE_ACTION_CANNOT_COMPLEMENT)).when(certificate).actions(
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

      doReturn(List.of(CERTIFICATE_ACTION_CANNOT_COMPLEMENT)).when(certificate).actions(
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

      doReturn(List.of(CERTIFICATE_ACTION_CANNOT_COMPLEMENT)).when(certificate).actions(
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

      doReturn(Collections.emptyList()).when(certificate).actions(
          Optional.of(ACTION_EVALUATION)
      );

      final var messageActions = message.actions(ACTION_EVALUATION, certificate);
      assertFalse(messageActions.contains(MessageActionFactory.cannotComplement()));
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

      doReturn(List.of(CERTIFICATE_ACTION_FORWARD)).when(certificate).actions(
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

      doReturn(List.of(CERTIFICATE_ACTION_FORWARD)).when(certificate).actions(
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

      doReturn(Collections.emptyList()).when(certificate).actions(
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
}
