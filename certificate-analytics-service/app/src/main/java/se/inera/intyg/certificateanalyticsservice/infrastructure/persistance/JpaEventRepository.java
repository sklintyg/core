package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.application.event.EventRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.EventEntityRepository;

@Repository
@RequiredArgsConstructor
public class JpaEventRepository implements EventRepository {

  private final EventEntityRepository eventEntityRepository;

  public EventEntity save(EventEntity event) {
    return eventEntityRepository.save(event);
  }
}
