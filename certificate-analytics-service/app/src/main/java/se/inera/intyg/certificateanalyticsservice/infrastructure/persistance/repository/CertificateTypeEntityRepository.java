package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;

public interface CertificateTypeEntityRepository extends
    CrudRepository<CertificateTypeEntity, Long> {

  Optional<CertificateTypeEntity> findByCertificateTypeAndCertificateTypeVersion(
      String certificateType, String certificateTypeVersion);
}
