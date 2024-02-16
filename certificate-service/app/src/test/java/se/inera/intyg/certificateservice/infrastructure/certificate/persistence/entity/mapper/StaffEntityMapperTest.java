package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_NAME;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;

class StaffEntityMapperTest {

  private static final Staff STAFF = Staff.builder()
      .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
      .name(
          Name.builder()
              .lastName(AJLA_DOCTOR_NAME)
              .build()
      )
      .build();

  private static final StaffEntity STAFF_ENTITY = StaffEntity.builder()
      .hsaId(AJLA_DOCTOR_HSA_ID)
      .name(AJLA_DOCTOR_NAME)
      .build();

  @Nested
  class ToEntity {

    @Test
    void shouldMapStaff() {
      assertEquals(STAFF_ENTITY,
          StaffEntityMapper.toEntity(STAFF)
      );
    }
  }

  @Nested
  class ToDomain {

    @Test
    void shouldMapStaff() {
      assertEquals(STAFF,
          StaffEntityMapper.toDomain(STAFF_ENTITY)
      );
    }
  }
}