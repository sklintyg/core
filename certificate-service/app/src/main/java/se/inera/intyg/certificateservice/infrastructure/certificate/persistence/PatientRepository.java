package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper.toDomain;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper.toEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

  private final PatientEntityRepository patientEntityRepository;
	private final PatientVersionEntityRepository patientVersionEntityRepository;

	public PatientEntity patient(Patient patient) {
		return patientEntityRepository.findById(patient.id().idWithoutDash())
				.map(patientEntity -> updatePatientVersion(patientEntity, patient))
				.orElseGet(() -> patientEntityRepository.save(toEntity(patient)));
  }

	private PatientEntity updatePatientVersion(PatientEntity patientEntity, Patient patient) {
		if (!patient.equals(toDomain(patientEntity))) {
			final var patientVersionEntity = PatientVersionEntityMapper.toEntity(patientEntity);
			final var existingVersions = patientVersionEntityRepository.
					findAllByIdOrderByValidFromDesc(patient.id().idWithoutDash());

			if (!existingVersions.isEmpty()) {
				patientVersionEntity.setValidFrom(existingVersions.getFirst().getValidTo());
			}
			final var newPatientVersion = toEntity(patient);

			patientEntity.setId(newPatientVersion.getId());
			patientEntity.setFirstName(newPatientVersion.getFirstName());
			patientEntity.setLastName(newPatientVersion.getLastName());
			patientEntity.setProtectedPerson(newPatientVersion.isProtectedPerson());
			patientEntity.setDeceased(newPatientVersion.isDeceased());
			patientEntity.setTestIndicated(newPatientVersion.isTestIndicated());
			patientEntity.setMiddleName(newPatientVersion.getMiddleName());
			patientEntity.setType(newPatientVersion.getType());

			patientVersionEntityRepository.save(patientVersionEntity);
			return patientEntityRepository.save(patientEntity);
		}
		return patientEntity;
	}
}
