package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;

public class UnitEntityMapper {

  public static UnitEntity map(String unitId) {
    return UnitEntity.builder()
        .hsaId(unitId)
        .build();
  }
}
