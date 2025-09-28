package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.DeviceEntity;

public class DeviceEntityMapper {

  public static DeviceEntity map() {
    return DeviceEntity.builder().build();
  }
}
