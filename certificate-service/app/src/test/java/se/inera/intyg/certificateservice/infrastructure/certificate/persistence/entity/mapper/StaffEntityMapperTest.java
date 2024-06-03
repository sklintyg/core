package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

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
      assertEquals(AJLA_DOKTOR, StaffEntityMapper.toDomain(AJLA_DOKTOR_ENTITY));
    }
  }
}