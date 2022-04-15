package se.inera.intyg.cts.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;

public interface TerminationEntityRepository extends CrudRepository<TerminationEntity, Long> {

  Optional<TerminationEntity> findByTerminationId(UUID terminationId);

  List<TerminationEntity> findAllByStatusIsIn(List<String> statuses);
}
