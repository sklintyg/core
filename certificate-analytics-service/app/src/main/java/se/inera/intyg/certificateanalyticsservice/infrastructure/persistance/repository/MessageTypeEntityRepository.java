package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.MessageTypeEntity;

@Repository
public interface MessageTypeEntityRepository extends
    CrudRepository<MessageTypeEntity, Long> {

  Optional<MessageTypeEntity> findByType(String messageType);
}