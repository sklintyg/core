package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRecipientEntity;

public class AdministrativeMessageRecipientEntityMapper {

  public static AdministrativeMessageRecipientEntity map(String recipient) {
    return AdministrativeMessageRecipientEntity.builder()
        .recipient(recipient)
        .build();
  }
}
