package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;

@Repository
public interface CertificateDataEntityRepository extends
    CrudRepository<CertificateDataEntity, Long> {

}
