package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientVersionEntity;

@Repository
public interface PatientVersionEntityRepository extends CrudRepository<PatientVersionEntity, Long>,
    JpaSpecificationExecutor<PatientVersionEntity> {

	List<PatientVersionEntity> findAllByIdOrderByValidFromDesc(String id);
}
