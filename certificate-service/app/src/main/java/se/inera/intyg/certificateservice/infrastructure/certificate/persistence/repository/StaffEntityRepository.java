package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;

@Repository
public interface StaffEntityRepository extends CrudRepository<StaffEntity, Long> {

  public StaffEntity findByHsaId(String hsaId);
}
