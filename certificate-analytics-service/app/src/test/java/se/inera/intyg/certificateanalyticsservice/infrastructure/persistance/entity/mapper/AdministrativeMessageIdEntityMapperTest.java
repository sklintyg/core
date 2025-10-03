package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageIdEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class AdministrativeMessageIdEntityMapperTest {

  @Test
  void shouldMapAdministrativeMessageIdCorrectly() {
    final var expected = AdministrativeMessageIdEntity.builder()
        .administrativeMessageId(TestDataConstants.ADMINISTRATIVE_MESSAGE_ID)
        .build();

    final var result = AdministrativeMessageIdEntityMapper.map(
        TestDataConstants.ADMINISTRATIVE_MESSAGE_ID);

    assertEquals(expected, result);
  }
}
