package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientVersionEntity;

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
        .build();
  }

}
