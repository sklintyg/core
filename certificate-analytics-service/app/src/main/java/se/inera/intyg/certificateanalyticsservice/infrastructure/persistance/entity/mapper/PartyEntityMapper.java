package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.PartyEntity;

public class PartyEntityMapper {

  public static PartyEntity map(String party) {
    return PartyEntity.builder()
        .party(party)
        .build();
  }
}
