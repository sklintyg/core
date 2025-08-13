package se.inera.intyg.certificateservice.application.message.dto;

import se.inera.intyg.certificateservice.domain.message.model.MessageType;

public enum MessageTypeDTO {
  AVSTMN, KONTKT, OVRIGT, PAMINN, KOMPLT;

  public MessageType toMessageType() {
    return switch (this) {
      case KOMPLT -> MessageType.COMPLEMENT;
      case PAMINN -> MessageType.REMINDER;
      case KONTKT -> MessageType.CONTACT;
      case OVRIGT -> MessageType.OTHER;
      case AVSTMN -> MessageType.COORDINATION;
    };
  }
}