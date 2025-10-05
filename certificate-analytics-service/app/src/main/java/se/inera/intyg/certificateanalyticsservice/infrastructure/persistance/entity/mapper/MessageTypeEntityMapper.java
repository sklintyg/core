package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.MessageTypeEntity;

public class MessageTypeEntityMapper {

  private MessageTypeEntityMapper() {
  }

  public static MessageTypeEntity map(String messageType) {
    return MessageTypeEntity.builder()
        .type(messageType)
        .build();
  }
}
