package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageTypeEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class AdministrativeMessageTypeEntityMapperTest {

  @Test
  void shouldMapAdministrativeMessageTypeCorrectly() {
    final var expected = AdministrativeMessageTypeEntity.builder()
        .type(TestDataConstants.ADMINISTRATIVE_MESSAGE_TYPE)
        .build();

    final var result = AdministrativeMessageTypeEntityMapper.map(
        TestDataConstants.ADMINISTRATIVE_MESSAGE_TYPE);

    assertEquals(expected, result);
  }
}
