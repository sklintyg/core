package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.DeviceEntity;

public class DeviceEntityMapperV1 {

  public static DeviceEntity map() {
    return DeviceEntity.builder().build();
  }
}
