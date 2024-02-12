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

}