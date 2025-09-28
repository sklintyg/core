package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;

public class UnitEntityMapperV1 {

  public static UnitEntity map(String unitId) {
    return UnitEntity.builder()
        .hsaId(unitId)
        .build();
  }
}
