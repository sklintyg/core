package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRelationEntity;

@Repository
public interface AdministrativeMessageRelationEntityRepository extends
    CrudRepository<AdministrativeMessageRelationEntity, Long> {

  Optional<AdministrativeMessageRelationEntity> findByRelationId(String relationId);
}