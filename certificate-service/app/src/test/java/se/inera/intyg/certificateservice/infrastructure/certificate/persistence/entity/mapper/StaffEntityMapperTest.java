package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ajlaDoctorBuilder;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

class StaffEntityMapperTest {

  private static final Staff AJLA_STAFF = ajlaDoctorBuilder()
      .paTitles(null)
      .specialities(null)
      .blocked(null)
      .build();


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
      assertEquals(AJLA_STAFF, StaffEntityMapper.toDomain(AJLA_DOKTOR_ENTITY));
    }
  }
}