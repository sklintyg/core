package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;

class StaffEntityMapperTest {

  private static final Staff STAFF = Staff.builder()
      .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
      .name(
          Name.builder()
              .firstName(AJLA_DOCTOR_FIRST_NAME)
              .middleName(AJLA_DOCTOR_MIDDLE_NAME)
              .lastName(AJLA_DOCTOR_LAST_NAME)
              .build()
      )
      .build();

  private static final StaffEntity STAFF_ENTITY = StaffEntity.builder()
      .hsaId(AJLA_DOCTOR_HSA_ID)
      .firstName(AJLA_DOCTOR_FIRST_NAME)
      .middleName(AJLA_DOCTOR_MIDDLE_NAME)
      .lastName(AJLA_DOCTOR_LAST_NAME)
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