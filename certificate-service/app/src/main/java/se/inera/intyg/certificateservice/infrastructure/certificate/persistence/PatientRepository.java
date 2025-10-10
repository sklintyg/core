package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper.toEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PatientRepository {

  private final PatientEntityRepository patientEntityRepository;
  private final MetadataVersionRepository metadataVersionRepository;
  private final EntityManager entityManager;

  public PatientEntity patient(Patient patient) {
    return patientEntityRepository.findById(patient.id().idWithoutDash())
        .map(patientEntity -> updatePatientVersion(patientEntity, patient))
        .orElseGet(() -> patientEntityRepository.save(toEntity(patient)));
  }

  private PatientEntity updatePatientVersion(PatientEntity patientEntity, Patient patient) {
    var newPatientEntity = toEntity(patient);
    if (patientEntity.hasDiff(newPatientEntity)) {
      try {
        metadataVersionRepository.savePatientVersion(patientEntity, newPatientEntity);
        entityManager.refresh(patientEntity);
      } catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
        log.info("Skipped updating PatientEntity because it was updated concurrently");

        entityManager.refresh(patientEntity);
      }
    }
    return patientEntity;
  }
}
