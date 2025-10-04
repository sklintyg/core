package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRecipientEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class AdministrativeMessagePartyEntityMapperTest {

  @Test
  void shouldMapAdministrativeMessageRecipientCorrectly() {
    final var expected = AdministrativeMessageRecipientEntity.builder()
        .recipient(TestDataConstants.MESSAGE_RECIPIENT)
        .build();

    final var result = AdministrativeMessageRecipientEntityMapper.map(
        TestDataConstants.MESSAGE_RECIPIENT);

    assertEquals(expected, result);
  }
}
