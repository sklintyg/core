package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;

@Repository
public interface UnitVersionEntityRepository extends CrudRepository<UnitVersionEntity, Long>,
    JpaSpecificationExecutor<UnitVersionEntity> {

	List<UnitVersionEntity> findAllByHsaId(String hsaId);
}
