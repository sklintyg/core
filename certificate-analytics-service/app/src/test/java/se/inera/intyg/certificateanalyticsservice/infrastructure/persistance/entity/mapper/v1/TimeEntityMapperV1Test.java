package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.TimeEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class TimeEntityMapperV1Test {

  @Test
  void shouldMapTimestampCorrectly() {
    final var ts = TestDataConstants.TIMESTAMP;
    final var expected = TimeEntity.builder()
        .date(ts)
        .year(ts.getYear())
        .month(ts.getMonthValue())
        .day(ts.getDayOfMonth())
        .hour(ts.getHour())
        .minute(ts.getMinute())
        .second(ts.getSecond())
        .build();
    final var result = TimeEntityMapperV1.map(ts);
    assertEquals(expected, result);
  }
}

