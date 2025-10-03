package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageIdEntity;

public class AdministrativeMessageIdEntityMapper {

  public static AdministrativeMessageIdEntity map(String administrativeMessageId) {
    return AdministrativeMessageIdEntity.builder()
        .administrativeMessageId(administrativeMessageId)
        .build();
  }
}
