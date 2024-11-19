package se.inera.intyg.certificateservice.domain.action.message.model;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;

public class MessageActionFactory {

  private MessageActionFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static MessageAction create(MessageActionSpecification actionSpecification) {
    return switch (actionSpecification.messageActionType()) {
      case COMPLEMENT -> MessageActionComplement.builder()
          .specification(actionSpecification)
          .build();
      case CANNOT_COMPLEMENT -> MessageActionCannotComplement.builder()
          .specification(actionSpecification)
          .build();
      case HANDLE_COMPLEMENT -> MessageActionHandleComplement.builder()
          .specification(actionSpecification)
          .build();
      case FORWARD -> MessageActionForward.builder()
          .specification(actionSpecification)
          .build();
      case ANSWER -> MessageActionAnswer.builder()
          .specification(actionSpecification)
          .build();
      case HANDLE_MESSAGE -> MessageActionHandleMessage.builder()
          .specification(actionSpecification)
          .build();
    };
  }
}
