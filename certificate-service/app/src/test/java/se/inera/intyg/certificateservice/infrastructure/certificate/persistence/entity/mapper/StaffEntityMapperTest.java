package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.HealthcareProfessionalLicenceEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PaTitleEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.SpecialityEmbeddable;

class StaffEntityMapperTest {

  @Nested
  class ToEntity {

    @Test
    void shouldMapStaff() {
      assertEquals(AJLA_DOKTOR_ENTITY, StaffEntityMapper.toEntity(AJLA_DOKTOR));
    }

    @Test
    void shouldReturnMutableListsForHibernateToWork() {
      final var entity = StaffEntityMapper.toEntity(AJLA_DOKTOR);
      assertAll(
          () -> assertDoesNotThrow(
              () -> entity.getPaTitles().add(PaTitleEmbeddable.builder().build())
          ),
          () -> assertDoesNotThrow(
              () -> entity.getSpecialities().add(SpecialityEmbeddable.builder().build())
          ),
          () -> assertDoesNotThrow(
              () -> entity.getHealthcareProfessionalLicences().add(
                  HealthcareProfessionalLicenceEmbeddable.builder().build()
              )
          )
      );
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