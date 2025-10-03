package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageTypeEntity;

@Repository
public interface AdministrativeMessageTypeEntityRepository extends
    CrudRepository<AdministrativeMessageTypeEntity, Byte> {

  Optional<AdministrativeMessageTypeEntity> findByType(String type);
}
