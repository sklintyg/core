package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper;

class StaffEntityMapperTest {

  private static final Staff STAFF = Staff.builder()
      .hsaId(new HsaId("HSA_ID"))
      .name(Name.builder()
          .firstName("FIRST")
          .lastName("LAST")
          .middleName("MIDDLE")
          .build()
      ).build();

  private static final StaffEntity ENTITY = StaffEntity.builder()
      .hsaId("HSA_ID")
      .name("NAME")
      .build();

  @Nested
  class ToEntity {

    @Test
    void shouldMapHsaId() {
      final var response = StaffEntityMapper.toEntity(STAFF);

      assertEquals(STAFF.hsaId().id(), response.getHsaId());
    }

    @Test
    void shouldMapName() {
      final var response = StaffEntityMapper.toEntity(STAFF);

      assertEquals(STAFF.name().fullName(), response.getName());
    }
  }

  @Nested
  class ToDomain {

    @Test
    void shouldMapHsaId() {
      final var response = StaffEntityMapper.toDomain(ENTITY);

      assertEquals(ENTITY.getHsaId(), response.hsaId().id());
    }
  }

}