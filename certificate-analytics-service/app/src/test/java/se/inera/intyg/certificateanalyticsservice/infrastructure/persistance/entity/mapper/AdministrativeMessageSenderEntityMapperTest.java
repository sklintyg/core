package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageSenderEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class AdministrativeMessageSenderEntityMapperTest {

  @Test
  void shouldMapAdministrativeMessageSenderCorrectly() {
    final var expected = AdministrativeMessageSenderEntity.builder()
        .sender(TestDataConstants.MESSAGE_SENDER)
        .build();

    final var result = AdministrativeMessageSenderEntityMapper.map(
        TestDataConstants.MESSAGE_SENDER);

    assertEquals(expected, result);
  }
}
