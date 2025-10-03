package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageSenderEntity;

public class AdministrativeMessageSenderEntityMapper {

  public static AdministrativeMessageSenderEntity map(String sender) {
    return AdministrativeMessageSenderEntity.builder()
        .sender(sender)
        .build();
  }
}
