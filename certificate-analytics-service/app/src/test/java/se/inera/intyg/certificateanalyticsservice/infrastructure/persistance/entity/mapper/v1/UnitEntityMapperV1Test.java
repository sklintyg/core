package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class UnitEntityMapperV1Test {

  @Test
  void shouldMapUnitIdCorrectly() {
    final var expected = UnitEntity.builder()
        .hsaId(TestDataConstants.UNIT_ID)
        .build();

    final var result = UnitEntityMapperV1.map(TestDataConstants.UNIT_ID);

    assertEquals(expected, result);
  }
}
