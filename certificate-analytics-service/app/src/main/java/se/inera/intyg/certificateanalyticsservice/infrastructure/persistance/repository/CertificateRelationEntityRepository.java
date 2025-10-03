package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateRelationEntity;

public interface CertificateRelationEntityRepository extends
    JpaRepository<CertificateRelationEntity, Long> {

}