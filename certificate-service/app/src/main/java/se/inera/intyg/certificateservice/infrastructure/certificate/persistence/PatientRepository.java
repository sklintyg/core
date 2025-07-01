package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

  private final PatientEntityRepository patientEntityRepository;

  public PatientEntity patient(Patient patient) {
    final var entity = patientEntityRepository.findById(patient.id().idWithoutDash())
        .map(existing ->
            PatientEntityMapper.updateEntity(existing, patient)
        )
        .orElseGet(
            () -> PatientEntityMapper.toEntity(patient)
        );

    return patientEntityRepository.save(entity);
  }
}