package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.SessionEntity;

public class SessionEntityMapperV1 {

  public static SessionEntity map(String sessionId) {
    return SessionEntity.builder()
        .sessionId(sessionId)
        .build();
  }
}
