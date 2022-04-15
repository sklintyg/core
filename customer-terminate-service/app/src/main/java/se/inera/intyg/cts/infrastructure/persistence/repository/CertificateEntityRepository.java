package se.inera.intyg.cts.infrastructure.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import se.inera.intyg.cts.infrastructure.persistence.entity.CertificateEntity;

public interface CertificateEntityRepository extends CrudRepository<CertificateEntity, Long> {

}
