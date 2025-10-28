package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.HealthcareProfessionalLicenceEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.HealthcareProfessionalLicenceVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PaTitleEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PaTitleVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.SpecialityEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.SpecialityVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRole;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRoleEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;

class StaffVersionEntityMapperTest {

  private static final StaffRoleEntity STAFF_ROLE_DOCTOR = StaffRoleEntity.builder()
      .role(StaffRole.DOCTOR.name())
      .key(StaffRole.DOCTOR.getKey())
      .build();

  private static final List<PaTitleEmbeddable> PA_TITLES = List.of(
      new PaTitleEmbeddable("203090", "Läkare legitimerad, annan"),
      new PaTitleEmbeddable("601010", "Kock")
  );

  private static final List<SpecialityEmbeddable> SPECIALITIES = List.of(
      new SpecialityEmbeddable("Allmänmedicin"),
      new SpecialityEmbeddable("Psykiatri")
  );

  private static final List<HealthcareProfessionalLicenceEmbeddable> LICENCES = List.of(
      new HealthcareProfessionalLicenceEmbeddable("Läkare")
  );

  private static final StaffEntity STAFF_ENTITY = getStaffEntity();
  private static final StaffEntity STAFF_ENTITY_WITH_NULL_LISTS = getStaffEntityWithNullLists();

  private static final StaffVersionEntity STAFF_VERSION_ENTITY = getStaffVersionEntity();
  private static final StaffVersionEntity STAFF_VERSION_ENTITY_WITH_NULL_LISTS = getStaffVersionEntityWithNullLists();

  @Nested
  class ToStaffVersion {

    @Test
    void shouldMapHsaId() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertEquals(STAFF_ENTITY.getHsaId(), response.getHsaId());
    }

    @Test
    void shouldMapFirstName() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertEquals(STAFF_ENTITY.getFirstName(), response.getFirstName());
    }

    @Test
    void shouldMapMiddleName() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertEquals(STAFF_ENTITY.getMiddleName(), response.getMiddleName());
    }

    @Test
    void shouldMapLastName() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertEquals(STAFF_ENTITY.getLastName(), response.getLastName());
    }

    @Test
    void shouldMapRole() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertEquals(STAFF_ENTITY.getRole(), response.getRole());
    }

    @Test
    void shouldMapPaTitles() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertEquals(STAFF_ENTITY.getPaTitles().size(), response.getPaTitles().size());
      for (int i = 0; i < STAFF_ENTITY.getPaTitles().size(); i++) {
        assertEquals(STAFF_ENTITY.getPaTitles().get(i).getCode(),
            response.getPaTitles().get(i).getCode());
        assertEquals(STAFF_ENTITY.getPaTitles().get(i).getDescription(),
            response.getPaTitles().get(i).getDescription());
      }
    }

    @Test
    void shouldMapSpecialities() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertEquals(STAFF_ENTITY.getSpecialities().size(), response.getSpecialities().size());
      for (int i = 0; i < STAFF_ENTITY.getSpecialities().size(); i++) {
        assertEquals(STAFF_ENTITY.getSpecialities().get(i).getSpeciality(),
            response.getSpecialities().get(i).getSpeciality());
      }
    }

    @Test
    void shouldMapHealthcareProfessionalLicences() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertEquals(STAFF_ENTITY.getHealthcareProfessionalLicences().size(),
          response.getHealthcareProfessionalLicences().size());
      for (int i = 0; i < STAFF_ENTITY.getHealthcareProfessionalLicences().size(); i++) {
        assertEquals(
            STAFF_ENTITY.getHealthcareProfessionalLicences().get(i)
                .getHealthcareProfessionalLicence(),
            response.getHealthcareProfessionalLicences().get(i).getHealthcareProfessionalLicence());
      }
    }

    @Test
    void shouldMapStaff() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertEquals(STAFF_ENTITY, response.getStaff());
    }

    @Test
    void shouldSetValidFromToNull() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      assertNull(response.getValidFrom());
    }

    @Test
    void shouldSetValidToToCurrentTime() {
      final var before = LocalDateTime.now();
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY);
      final var after = LocalDateTime.now();

      assertNotNull(response.getValidTo());
      assertTrue(response.getValidTo().isAfter(before.minusSeconds(1)));
      assertTrue(response.getValidTo().isBefore(after.plusSeconds(1)));
    }

    @Test
    void shouldMapNullPaTitlesToEmptyList() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY_WITH_NULL_LISTS);
      assertNotNull(response.getPaTitles());
      assertTrue(response.getPaTitles().isEmpty());
    }

    @Test
    void shouldMapNullSpecialitiesToEmptyList() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY_WITH_NULL_LISTS);
      assertNotNull(response.getSpecialities());
      assertTrue(response.getSpecialities().isEmpty());
    }

    @Test
    void shouldMapNullHealthcareProfessionalLicencesToEmptyList() {
      final var response = StaffVersionEntityMapper.toStaffVersion(STAFF_ENTITY_WITH_NULL_LISTS);
      assertNotNull(response.getHealthcareProfessionalLicences());
      assertTrue(response.getHealthcareProfessionalLicences().isEmpty());
    }
  }

  @Nested
  class ToStaff {

    @Test
    void shouldMapHsaId() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY);
      assertEquals(STAFF_VERSION_ENTITY.getHsaId(), response.getHsaId());
    }

    @Test
    void shouldMapFirstName() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY);
      assertEquals(STAFF_VERSION_ENTITY.getFirstName(), response.getFirstName());
    }

    @Test
    void shouldMapMiddleName() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY);
      assertEquals(STAFF_VERSION_ENTITY.getMiddleName(), response.getMiddleName());
    }

    @Test
    void shouldMapLastName() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY);
      assertEquals(STAFF_VERSION_ENTITY.getLastName(), response.getLastName());
    }

    @Test
    void shouldMapRole() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY);
      assertEquals(STAFF_VERSION_ENTITY.getRole(), response.getRole());
    }

    @Test
    void shouldMapPaTitles() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY);
      assertEquals(STAFF_VERSION_ENTITY.getPaTitles().size(), response.getPaTitles().size());
      for (int i = 0; i < STAFF_VERSION_ENTITY.getPaTitles().size(); i++) {
        assertEquals(STAFF_VERSION_ENTITY.getPaTitles().get(i).getCode(),
            response.getPaTitles().get(i).getCode());
        assertEquals(STAFF_VERSION_ENTITY.getPaTitles().get(i).getDescription(),
            response.getPaTitles().get(i).getDescription());
      }
    }

    @Test
    void shouldMapSpecialities() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY);
      assertEquals(STAFF_VERSION_ENTITY.getSpecialities().size(),
          response.getSpecialities().size());
      for (int i = 0; i < STAFF_VERSION_ENTITY.getSpecialities().size(); i++) {
        assertEquals(STAFF_VERSION_ENTITY.getSpecialities().get(i).getSpeciality(),
            response.getSpecialities().get(i).getSpeciality());
      }
    }

    @Test
    void shouldMapHealthcareProfessionalLicences() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY);
      assertEquals(STAFF_VERSION_ENTITY.getHealthcareProfessionalLicences().size(),
          response.getHealthcareProfessionalLicences().size());
      for (int i = 0; i < STAFF_VERSION_ENTITY.getHealthcareProfessionalLicences().size(); i++) {
        assertEquals(
            STAFF_VERSION_ENTITY.getHealthcareProfessionalLicences().get(i)
                .getHealthcareProfessionalLicence(),
            response.getHealthcareProfessionalLicences().get(i)
                .getHealthcareProfessionalLicence());
      }
    }

    @Test
    void shouldMapNullPaTitlesToEmptyList() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY_WITH_NULL_LISTS);
      assertNotNull(response.getPaTitles());
      assertTrue(response.getPaTitles().isEmpty());
    }

    @Test
    void shouldMapNullSpecialitiesToEmptyList() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY_WITH_NULL_LISTS);
      assertNotNull(response.getSpecialities());
      assertTrue(response.getSpecialities().isEmpty());
    }

    @Test
    void shouldMapNullHealthcareProfessionalLicencesToEmptyList() {
      final var response = StaffVersionEntityMapper.toStaff(STAFF_VERSION_ENTITY_WITH_NULL_LISTS);
      assertNotNull(response.getHealthcareProfessionalLicences());
      assertTrue(response.getHealthcareProfessionalLicences().isEmpty());
    }
  }

  private static StaffEntity getStaffEntity() {
    return StaffEntity.builder()
        .key(1)
        .hsaId(AJLA_DOCTOR_HSA_ID)
        .firstName(AJLA_DOCTOR_FIRST_NAME)
        .middleName(AJLA_DOCTOR_MIDDLE_NAME)
        .lastName(AJLA_DOCTOR_LAST_NAME)
        .role(STAFF_ROLE_DOCTOR)
        .paTitles(PA_TITLES)
        .specialities(SPECIALITIES)
        .healthcareProfessionalLicences(LICENCES)
        .build();
  }

  private static StaffEntity getStaffEntityWithNullLists() {
    return StaffEntity.builder()
        .key(2)
        .hsaId(AJLA_DOCTOR_HSA_ID)
        .firstName(AJLA_DOCTOR_FIRST_NAME)
        .middleName(AJLA_DOCTOR_MIDDLE_NAME)
        .lastName(AJLA_DOCTOR_LAST_NAME)
        .role(STAFF_ROLE_DOCTOR)
        .paTitles(null)
        .specialities(null)
        .healthcareProfessionalLicences(null)
        .build();
  }

  private static StaffVersionEntity getStaffVersionEntity() {
    return StaffVersionEntity.builder()
        .key(1)
        .hsaId(AJLA_DOCTOR_HSA_ID)
        .firstName(AJLA_DOCTOR_FIRST_NAME)
        .middleName(AJLA_DOCTOR_MIDDLE_NAME)
        .lastName(AJLA_DOCTOR_LAST_NAME)
        .role(STAFF_ROLE_DOCTOR)
        .paTitles(List.of(
            new PaTitleVersionEmbeddable("203090", "Läkare legitimerad, annan"),
            new PaTitleVersionEmbeddable("601010", "Kock")
        ))
        .specialities(List.of(
            new SpecialityVersionEmbeddable("Allmänmedicin"),
            new SpecialityVersionEmbeddable("Psykiatri")
        ))
        .healthcareProfessionalLicences(List.of(
            new HealthcareProfessionalLicenceVersionEmbeddable("Läkare")
        ))
        .validFrom(LocalDateTime.of(2024, 1, 1, 0, 0))
        .validTo(LocalDateTime.of(2024, 12, 31, 23, 59))
        .staff(getStaffEntity())
        .build();
  }

  private static StaffVersionEntity getStaffVersionEntityWithNullLists() {
    return StaffVersionEntity.builder()
        .key(2)
        .hsaId(AJLA_DOCTOR_HSA_ID)
        .firstName(AJLA_DOCTOR_FIRST_NAME)
        .middleName(AJLA_DOCTOR_MIDDLE_NAME)
        .lastName(AJLA_DOCTOR_LAST_NAME)
        .role(STAFF_ROLE_DOCTOR)
        .paTitles(null)
        .specialities(null)
        .healthcareProfessionalLicences(null)
        .validFrom(LocalDateTime.of(2024, 1, 1, 0, 0))
        .validTo(LocalDateTime.of(2024, 12, 31, 23, 59))
        .staff(getStaffEntityWithNullLists())
        .build();
  }
}

