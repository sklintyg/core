package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageEntity;

@Repository
public interface AdministrativeMessageEntityRepository extends
    CrudRepository<AdministrativeMessageEntity, Long> {

  Optional<AdministrativeMessageEntity> findByAdministrativeMessageId(String messageId);
}