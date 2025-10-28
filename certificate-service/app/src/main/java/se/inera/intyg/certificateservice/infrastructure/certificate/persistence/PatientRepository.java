package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper.toEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  public PatientEntity patient(Patient patient) {
    return patientEntityRepository.findById(patient.id().idWithoutDash())
        .map(patientEntity -> updatePatientVersion(patientEntity, patient))
        .orElseGet(() -> patientEntityRepository.save(toEntity(patient)));
  }

  private PatientEntity updatePatientVersion(PatientEntity patientEntity, Patient patient) {
    final var newPatientEntity = toEntity(patient);
    if (patientEntity.hasDiff(newPatientEntity)) {
      return metadataVersionRepository.savePatientVersion(patientEntity, newPatientEntity);
    }
    return patientEntity;
  }
}
