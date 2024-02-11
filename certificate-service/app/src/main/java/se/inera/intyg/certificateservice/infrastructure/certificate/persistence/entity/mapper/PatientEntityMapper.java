package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PersonIdType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;

public class PatientEntityMapper {

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
