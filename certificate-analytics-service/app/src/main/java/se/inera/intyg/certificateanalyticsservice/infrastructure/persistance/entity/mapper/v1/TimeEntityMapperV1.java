package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import java.time.LocalDateTime;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.TimeEntity;

public class TimeEntityMapperV1 {

  public static TimeEntity map(LocalDateTime timestamp) {
    return TimeEntity.builder()
        .date(timestamp)
        .year(timestamp.getYear())
        .month(timestamp.getMonthValue())
        .day(timestamp.getDayOfMonth())
        .hour(timestamp.getHour())
        .minute(timestamp.getMinute())
        .second(timestamp.getSecond())
        .build();
  }
}
