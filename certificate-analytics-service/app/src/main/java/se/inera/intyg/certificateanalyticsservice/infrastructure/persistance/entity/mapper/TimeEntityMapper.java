package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import java.time.LocalDateTime;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.TimeEntity;

public class TimeEntityMapper {

  public static TimeEntity map(LocalDateTime timestamp) {
    return TimeEntity.builder()
        .timestamp(timestamp)
        .build();
  }
}
