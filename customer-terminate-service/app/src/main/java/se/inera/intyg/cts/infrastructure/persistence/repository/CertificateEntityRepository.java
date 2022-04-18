package se.inera.intyg.cts.infrastructure.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import se.inera.intyg.cts.infrastructure.persistence.entity.CertificateEntity;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;

public interface CertificateEntityRepository extends CrudRepository<CertificateEntity, Long> {

  List<CertificateEntity> findAllByTermination(TerminationEntity terminationEntity);
}
