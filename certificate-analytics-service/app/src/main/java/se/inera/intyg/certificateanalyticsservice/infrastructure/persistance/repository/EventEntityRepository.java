package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;

@Repository
public interface EventEntityRepository extends CrudRepository<EventEntity, Integer> {

}

