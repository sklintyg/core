package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;

@Repository
public interface CertificateEntityRepository extends CrudRepository<CertificateEntity, Long>,
    JpaSpecificationExecutor<CertificateEntity> {


  Optional<CertificateEntity> findByCertificateId(String certificateId);

  List<CertificateEntity> findCertificateEntitiesByCertificateIdIn(
      List<String> certificateId);

  void deleteAllByCertificateIdIn(List<String> certificateIds);

  @Query("SELECT c FROM CertificateEntity c WHERE c.careProvider.hsaId = :careProviderHsaId AND c.signed IS NOT null")
  Page<CertificateEntity> findSignedCertificateEntitiesByCareProviderHsaId(@Param("careProviderHsaId") String careProviderHsaId, Pageable pageable);

  @Query("SELECT c FROM CertificateEntity c WHERE c.careProvider.hsaId = :careProviderHsaId")
  Page<CertificateEntity> findCertificateEntitiesByCareProviderHsaId(@Param("careProviderHsaId") String careProviderHsaId, Pageable pageable);

  @Query("SELECT count(c.certificateId) FROM CertificateEntity c WHERE c.careProvider.hsaId = :careProviderHsaId AND c.revoked IS NOT null")
  long findRevokedCertificateEntitiesByCareProviderHsaId(@Param("careProviderHsaId") String careProviderHsaId);

}