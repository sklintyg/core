package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ajlaDoctorBuilder;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StaffEntityMapperTest {

  @Nested
  class ToEntity {

    @Test
    void shouldMapStaff() {
      assertEquals(AJLA_DOKTOR_ENTITY, StaffEntityMapper.toEntity(AJLA_DOKTOR));
    }
  }

  @Nested
  class ToDomain {

    @Test
    void shouldMapStaff() {

      final var expectedStaff = ajlaDoctorBuilder()
          .paTitles(null)
          .specialities(null)
          .blocked(null)
          .build();

      assertEquals(expectedStaff, StaffEntityMapper.toDomain(AJLA_DOKTOR_ENTITY));
    }
  }
}