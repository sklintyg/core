package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class UserEntityMapperTest {

  @Test
  void shouldMapStaffIdCorrectly() {
    final var expected = UserEntity.builder()
        .userId(TestDataConstants.STAFF_ID)
        .build();
    final var result = UserEntityMapper.map(TestDataConstants.STAFF_ID);
    assertEquals(expected, result);
  }
}

