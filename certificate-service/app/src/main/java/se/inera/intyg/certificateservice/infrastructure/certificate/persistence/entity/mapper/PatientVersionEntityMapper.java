package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PersonEntityIdType;

public class PatientVersionEntityMapper {

  private PatientVersionEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static PatientVersionEntity toEntity(PatientEntity patientEntity) {
    return PatientVersionEntity.builder()
        .id(patientEntity.getId())
				.type(patientEntity.getType())
				.firstName(patientEntity.getFirstName())
				.middleName(patientEntity.getMiddleName())
				.lastName(patientEntity.getLastName())
				.validFrom(null)
				.validTo(LocalDateTime.now())
				.protectedPerson(patientEntity.isProtectedPerson())
				.deceased(patientEntity.isDeceased())
				.testIndicated(patientEntity.isTestIndicated())
				.patient(patientEntity)
        .build();
  }

}
