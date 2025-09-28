package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class UserEntityMapperV1Test {

  @Test
  void shouldMapStaffIdCorrectly() {
    final var expected = UserEntity.builder()
        .userId(TestDataConstants.STAFF_ID)
        .build();
    final var result = UserEntityMapperV1.map(TestDataConstants.STAFF_ID);
    assertEquals(expected, result);
  }
}

