package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RecipientEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

class RecipientEntityMapperTest {

  @Test
  void shouldMapRecipientCorrectly() {
    final var expected = RecipientEntity.builder()
        .recipient(TestDataConstants.RECIPIENT)
        .build();

    final var result = RecipientEntityMapper.map(TestDataConstants.RECIPIENT);

    assertEquals(expected, result);
  }
}