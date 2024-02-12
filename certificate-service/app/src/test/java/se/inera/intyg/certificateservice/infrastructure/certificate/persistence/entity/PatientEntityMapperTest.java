package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;

class PatientEntityMapperTest {

  private static final PatientEntity PATIENT_ENTITY = PatientEntity.builder()
      .id("ID")
      .build();

  private static final Patient PATIENT = Patient.builder()
      .id(PersonId.builder()
          .id("ID")
          .build())
      .build();

  @Nested
  class toEntity {

    @Test
    void shouldMapId() {
      final var response = PatientEntityMapper.toEntity(PATIENT);
      assertEquals(PATIENT.id().id(), response.getId());
    }
  }

  @Nested
  class toDomain {

    @Test
    void shouldMapId() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY);
      assertEquals(PATIENT_ENTITY.getId(), response.id().id());
    }
  }

}