package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;

@Repository
public interface PatientEntityRepository extends CrudRepository<PatientEntity, Long>,
    JpaSpecificationExecutor<PatientEntity> {

  Optional<PatientEntity> findById(String patientId);
}
