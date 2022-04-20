package se.inera.intyg.cts.infrastructure.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import se.inera.intyg.cts.infrastructure.persistence.entity.CertificateTextEntity;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;

public interface CertificateTextEntityRepository extends
    CrudRepository<CertificateTextEntity, Long> {

  List<CertificateTextEntity> findAllByTermination(TerminationEntity terminationEntity);
}
