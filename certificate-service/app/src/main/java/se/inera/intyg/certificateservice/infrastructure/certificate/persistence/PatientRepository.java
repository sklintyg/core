package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper.toDomain;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper.toEntity;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PatientRepository {

	private final PatientEntityRepository patientEntityRepository;
	private final PatientVersionEntityRepository patientVersionEntityRepository;

	@Transactional
	public PatientEntity patient(Patient patient) {
		return patientEntityRepository.findById(patient.id().idWithoutDash())
				.map(patientEntity -> updatePatientVersion(patientEntity, patient))
				.orElseGet(() -> patientEntityRepository.save(toEntity(patient)));
	}

	private PatientEntity updatePatientVersion(PatientEntity patientEntity, Patient patient) {
		var newPatientEntity = toEntity(patient);
		if (!patientEntity.equals(newPatientEntity)) {
			return savePatientVersion(patientEntity, newPatientEntity);
		}
		return patientEntity;
	}

	private PatientEntity savePatientVersion(PatientEntity patientEntity, PatientEntity newPatientEntity) {
		try {
			copyValues(patientEntity, newPatientEntity);
			var result = patientEntityRepository.save(patientEntity);
			updatePatientVersionHistory(result);
			return result;

		} catch (OptimisticLockException e) {
			log.info("Skipped updating PatientEntity {} because it was updated concurrently", patientEntity.getId());
			return patientEntityRepository.findById(patientEntity.getId()).orElse(patientEntity);
		}
	}

	private void copyValues(PatientEntity target, PatientEntity source) {
		target.setFirstName(source.getFirstName());
		target.setMiddleName(source.getMiddleName());
		target.setLastName(source.getLastName());
		target.setProtectedPerson(source.isProtectedPerson());
		target.setDeceased(source.isDeceased());
		target.setTestIndicated(source.isTestIndicated());
		target.setType(source.getType());
	}

	private void updatePatientVersionHistory(PatientEntity patientEntity) {
		final var patientVersionEntity = PatientVersionEntityMapper.toEntity(patientEntity);
		final var existingVersions =
				patientVersionEntityRepository.findAllByIdOrderByValidFromDesc(patientEntity.getId());

		if (!existingVersions.isEmpty()) {
			patientVersionEntity.setValidFrom(existingVersions.getFirst().getValidTo());
		}

		patientVersionEntityRepository.save(patientVersionEntity);
	}
}
