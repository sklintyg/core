package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;

@Repository
public interface CertificateEntityRepository extends CrudRepository<CertificateEntity, Long>,
    JpaSpecificationExecutor<CertificateEntity> {


  Optional<CertificateEntity> findByCertificateId(String certificateId);

  List<CertificateEntity> findCertificateEntitiesByCertificateIdIn(
      List<String> certificateId);

  void deleteAllByCertificateIdIn(List<String> certificateIds);

  List<CertificateEntity> findCertificateEntitiesByCreatedBeforeAndAndStatus(
      LocalDateTime cutoffDate, String status);
}
