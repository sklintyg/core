package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageIdEntity;

@Repository
public interface AdministrativeMessageIdEntityRepository extends
    CrudRepository<AdministrativeMessageIdEntity, Long> {

  Optional<AdministrativeMessageIdEntity> findByAdministrativeMessageId(
      String administrativeMessageId);
}
