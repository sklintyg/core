package se.inera.intyg.certificateservice.domain.action.message.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

class MessageActionFactoryTest {

  @Test
  void shallIncludeActionComplement() {
    final var messageActionSpecification = MessageActionSpecification.builder()
        .messageActionType(MessageActionType.COMPLEMENT)
        .build();

    final var messageAction = MessageActionFactory.create(messageActionSpecification);
    assertEquals(messageAction.getClass(), MessageActionComplement.class);
  }

  @Test
  void shallIncludeActionCannotComplement() {
    final var messageActionSpecification = MessageActionSpecification.builder()
        .messageActionType(MessageActionType.CANNOT_COMPLEMENT)
        .build();

    final var messageAction = MessageActionFactory.create(messageActionSpecification);
    assertEquals(messageAction.getClass(), MessageActionCannotComplement.class);
  }

  @Test
  void shallIncludeActionHandleComplement() {
    final var messageActionSpecification = MessageActionSpecification.builder()
        .messageActionType(MessageActionType.HANDLE_COMPLEMENT)
        .build();

    final var messageAction = MessageActionFactory.create(messageActionSpecification);
    assertEquals(messageAction.getClass(), MessageActionHandleComplement.class);
  }

  @Test
  void shallIncludeActionForward() {
    final var messageActionSpecification = MessageActionSpecification.builder()
        .messageActionType(MessageActionType.FORWARD)
        .build();

    final var messageAction = MessageActionFactory.create(messageActionSpecification);
    assertEquals(messageAction.getClass(), MessageActionForward.class);
  }

  @Test
  void shallIncludeActionAnswer() {
    final var messageActionSpecification = MessageActionSpecification.builder()
        .messageActionType(MessageActionType.ANSWER)
        .build();

    final var messageAction = MessageActionFactory.create(messageActionSpecification);
    assertEquals(messageAction.getClass(), MessageActionAnswer.class);
  }

  @Test
  void shallIncludeActionHandleMessage() {
    final var messageActionSpecification = MessageActionSpecification.builder()
        .messageActionType(MessageActionType.HANDLE_MESSAGE)
        .build();

    final var messageAction = MessageActionFactory.create(messageActionSpecification);
    assertEquals(messageAction.getClass(), MessageActionHandleMessage.class);
  }
}
