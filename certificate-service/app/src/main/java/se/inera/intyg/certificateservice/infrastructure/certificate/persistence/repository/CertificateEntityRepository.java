package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;

@Repository
public interface CertificateEntityRepository extends CrudRepository<CertificateEntity, Long> {

  CertificateEntity findByCertificateId(String certificateId);

  void deleteAllByCertificateIdIn(List<String> certificateIds);
}
