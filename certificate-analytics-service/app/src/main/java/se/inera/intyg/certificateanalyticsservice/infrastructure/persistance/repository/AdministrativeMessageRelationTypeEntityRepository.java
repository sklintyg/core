package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRelationTypeEntity;

@Repository
public interface AdministrativeMessageRelationTypeEntityRepository extends
    CrudRepository<AdministrativeMessageRelationTypeEntity, Long> {

  Optional<AdministrativeMessageRelationTypeEntity> findByRelationType(String relationType);
}

