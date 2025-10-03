package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageTypeEntity;

public class AdministrativeMessageTypeEntityMapper {

  public static AdministrativeMessageTypeEntity map(String type) {
    return AdministrativeMessageTypeEntity.builder()
        .type(type)
        .build();
  }
}
