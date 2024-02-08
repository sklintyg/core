package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;

@Repository
public interface CertificateModelEntityRepository extends
    CrudRepository<CertificateModelEntity, Long> {

}
