package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PersonIdType;

public class PatientEntityMapper {

  private PatientEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static PatientEntity toEntity(Patient patient) {
    final var type = PersonIdType.valueOf(patient.id().type().name());

    return PatientEntity.builder()
        .id(patient.id().id())
        .type(
            PatientIdTypeEntity.builder()
                .key(type.getKey())
                .type(type.name())
                .build()
        )
        .build();
  }
}
