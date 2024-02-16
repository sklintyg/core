package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;

@Repository
public interface CertificateEntityRepository extends CrudRepository<CertificateEntity, Long> {


  Optional<CertificateEntity> findByCertificateId(String certificateId);

  List<CertificateEntity> findCertificateEntitiesByPatientAndCareUnit(PatientEntity patient,
      UnitEntity careUnit);

  List<CertificateEntity> findCertificateEntitiesByPatientAndIssuedOnUnit(PatientEntity patient,
      UnitEntity issuedOnUnit);

  void deleteAllByCertificateIdIn(List<String> certificateIds);
}
