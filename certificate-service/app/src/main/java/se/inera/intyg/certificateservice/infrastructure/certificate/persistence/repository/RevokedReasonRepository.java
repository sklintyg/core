package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReasonEntity;

@Repository
public interface RevokedReasonRepository extends CrudRepository<RevokedReasonEntity, Long> {

  Optional<RevokedReasonEntity> findByReason(String reason);
}
