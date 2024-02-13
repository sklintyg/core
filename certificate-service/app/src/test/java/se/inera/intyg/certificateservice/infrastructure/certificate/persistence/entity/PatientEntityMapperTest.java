package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper;

class PatientEntityMapperTest {

  private static final Patient PATIENT = Patient.builder()
      .id(PersonId.builder()
          .id("ID")
          .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
          .build())
      .build();

  private static final PatientEntity PATIENT_ENTITY = PatientEntity.builder()
      .id("ID")
      .type(PatientIdTypeEntity.builder()
          .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
          .key(1)
          .build())
      .build();


  @Nested
  class ToEntity {

    @Test
    void shouldMapId() {
      final var response = PatientEntityMapper.toEntity(PATIENT);
      assertEquals(PATIENT.id().id(), response.getId());
    }

    @Test
    void shouldMapType() {
      final var response = PatientEntityMapper.toEntity(PATIENT);
      assertEquals(PATIENT.id().type().name(), response.getType().getType());
    }
  }

  @Nested
  class ToDomain {

    @Test
    void shouldMapId() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY);
      assertEquals(PATIENT_ENTITY.getId(), response.id().id());
    }

    @Test
    void shouldMapType() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY);
      assertEquals(PATIENT_ENTITY.getType().getType(), response.id().type().name());
    }
  }

}