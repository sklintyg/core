package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PersonEntityIdType;

public class PatientEntityMapper {

  private PatientEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static PatientEntity toEntity(Patient patient) {
    final var type = PersonEntityIdType.valueOf(patient.id().type().name());

    return PatientEntity.builder()
        .id(patient.id().idWithoutDash())
        .type(
            PatientIdTypeEntity.builder()
                .key(type.getKey())
                .type(type.name())
                .build()
        )
        .firstName(patient.name().firstName())
        .lastName(patient.name().lastName())
        .middleName(patient.name().middleName())
        .testIndicated(patient.testIndicated().value())
        .protectedPerson(patient.protectedPerson().value())
        .deceased(patient.deceased().value())
        .build();
  }

  public static Patient toDomain(PatientEntity patientEntity) {

    return Patient.builder()
        .id(
            PersonId.builder()
                .id(patientEntity.getId())
                .type(PersonIdType.valueOf(patientEntity.getType().getType()))
                .build()
        )
        .protectedPerson(new ProtectedPerson(patientEntity.isProtectedPerson()))
        .name(
            Name.builder()
                .lastName(patientEntity.getLastName())
                .middleName(patientEntity.getMiddleName())
                .firstName(patientEntity.getFirstName())
                .build()
        )
        .deceased(new Deceased(patientEntity.isDeceased()))
        .testIndicated(new TestIndicated(patientEntity.isTestIndicated()))
        .build();
  }
}
