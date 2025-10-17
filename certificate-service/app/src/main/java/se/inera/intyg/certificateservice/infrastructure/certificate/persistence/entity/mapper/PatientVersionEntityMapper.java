package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientVersionEntity;

public class PatientVersionEntityMapper {

  private PatientVersionEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static PatientVersionEntity toPatientVersion(PatientEntity patientEntity) {
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

  public static PatientEntity toPatient(PatientVersionEntity patientVersionEntity) {
    return PatientEntity.builder()
        .id(patientVersionEntity.getId())
        .type(patientVersionEntity.getType())
        .firstName(patientVersionEntity.getFirstName())
        .middleName(patientVersionEntity.getMiddleName())
        .lastName(patientVersionEntity.getLastName())
        .protectedPerson(patientVersionEntity.isProtectedPerson())
        .deceased(patientVersionEntity.isDeceased())
        .testIndicated(patientVersionEntity.isTestIndicated())
        .build();
  }

}
