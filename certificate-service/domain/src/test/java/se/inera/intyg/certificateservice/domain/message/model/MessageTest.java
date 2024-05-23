package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MessageTest {

  @Test
  void shallIncludeMessageActionComplementAndCannotComplementIfTypeIsComplementAndStatusUnhandled() {
    final var message = Message.builder()
        .type(MessageType.COMPLEMENT)
        .status(MessageStatus.SENT)
        .build();

    final var messageActions = message.availableActions();
    assertAll(
        () -> assertTrue(messageActions.contains(MessageActionFactory.complement())),
        () -> assertTrue(messageActions.contains(MessageActionFactory.cannotComplement()))
    );
  }

  @Test
  void shallIncludeMessageActionForwardIfStatusUnhandled() {
    final var message = Message.builder()
        .type(MessageType.COMPLEMENT)
        .status(MessageStatus.SENT)
        .build();

    final var messageActions = message.availableActions();
    assertTrue(messageActions.contains(MessageActionFactory.forward()));
  }

  @Test
  void shallExcluideMessageActionForwardIfStatusHandled() {
    final var message = Message.builder()
        .type(MessageType.COMPLEMENT)
        .status(MessageStatus.HANDLED)
        .build();

    final var messageActions = message.availableActions();
    assertFalse(messageActions.contains(MessageActionFactory.forward()));
  }


  @Test
  void shallExcludeMessageActionComplementAndCannotComplementIfWrongType() {
    final var message = Message.builder()
        .type(MessageType.REMINDER)
        .status(MessageStatus.SENT)
        .build();

    final var messageActions = message.availableActions();
    assertAll(
        () -> assertFalse(messageActions.contains(MessageActionFactory.complement())),
        () -> assertFalse(messageActions.contains(MessageActionFactory.cannotComplement()))
    );
  }

  @Test
  void shallExcludeMessageActionComplementAndCannotComplementIfHandled() {
    final var message = Message.builder()
        .type(MessageType.COMPLEMENT)
        .status(MessageStatus.HANDLED)
        .build();

    final var messageActions = message.availableActions();
    assertAll(
        () -> assertFalse(messageActions.contains(MessageActionFactory.complement())),
        () -> assertFalse(messageActions.contains(MessageActionFactory.cannotComplement()))
    );
  }
}
