package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.SessionEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.SessionEntityMapperV1;

@Repository
@RequiredArgsConstructor
public class SessionRepository {

  private final SessionEntityRepository sessionEntityRepository;

  public SessionEntity findOrCreate(String sessionId) {
    return sessionEntityRepository.findBySessionId(sessionId)
        .orElseGet(() -> sessionEntityRepository.save(SessionEntityMapperV1.map(sessionId)));
  }
}

