package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;

public interface EventEntityRepository extends CrudRepository<EventEntity, Integer> {

}

