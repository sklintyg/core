package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.EventTypeEntityMapperV1;

@Repository
@RequiredArgsConstructor
public class EventTypeRepository {

  private final EventTypeEntityRepository eventTypeEntityRepository;

  public EventTypeEntity findOrCreate(String eventType) {
    return eventTypeEntityRepository.findByEventType(eventType)
        .orElseGet(() -> eventTypeEntityRepository.save(EventTypeEntityMapperV1.map(eventType)));
  }
}

