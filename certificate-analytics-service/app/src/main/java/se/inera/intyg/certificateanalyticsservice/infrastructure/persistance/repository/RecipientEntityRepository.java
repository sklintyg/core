package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RecipientEntity;

@Repository
public interface RecipientEntityRepository extends CrudRepository<RecipientEntity, Long> {

  Optional<RecipientEntity> findByRecipient(String recipient);
}

