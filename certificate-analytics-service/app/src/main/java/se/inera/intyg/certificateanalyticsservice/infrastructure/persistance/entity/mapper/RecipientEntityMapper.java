package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RecipientEntity;

public class RecipientEntityMapper {

  public static RecipientEntity map(String recipient) {
    return RecipientEntity.builder()
        .recipient(recipient)
        .build();
  }
}
