package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateRelationTypeEntity;

public interface CertificateRelationTypeEntityRepository extends
    JpaRepository<CertificateRelationTypeEntity, Long> {

  Optional<CertificateRelationTypeEntity> findByRelationType(String relationType);
}