package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.HealthcareProfessionalLicenceEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.HealthcareProfessionalLicenceVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PaTitleEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PaTitleVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.SpecialityEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.SpecialityVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRoleEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;

class StaffVersionEntityMapperTest {

  private StaffRoleEntity staffRole;
  private StaffEntity staffEntity;
  private StaffVersionEntity staffVersionEntity;

  @BeforeEach
  void setUp() {
    staffRole = StaffRoleEntity.builder()
        .key(1)
        .role("DOCTOR")
        .build();
  }

  @Nested
  class ToStaffVersionTests {

    @BeforeEach
    void setUp() {
      staffEntity = StaffEntity.builder()
          .key(1)
          .hsaId("HSA123")
          .firstName("John")
          .middleName("Middle")
          .lastName("Doe")
          .role(staffRole)
          .paTitles(List.of(new PaTitleEmbeddable("CODE1", "Description1")))
          .specialities(List.of(new SpecialityEmbeddable("Cardiology")))
          .healthcareProfessionalLicences(
              List.of(new HealthcareProfessionalLicenceEmbeddable("LICENSE1")))
          .build();
    }

    @Test
    void shouldMapStaffEntityToStaffVersionEntity() {
      final var result = StaffVersionEntityMapper.toStaffVersion(staffEntity);

      assertEquals(staffEntity.getHsaId(), result.getHsaId());
      assertEquals(staffEntity.getFirstName(), result.getFirstName());
      assertEquals(staffEntity.getMiddleName(), result.getMiddleName());
      assertEquals(staffEntity.getLastName(), result.getLastName());
      assertEquals(staffEntity.getRole(), result.getRole());
      assertEquals(staffEntity, result.getStaff());
    }

    @Test
    void shouldSetValidFromToNull() {
      final var result = StaffVersionEntityMapper.toStaffVersion(staffEntity);

      assertNull(result.getValidFrom());
    }

    @Test
    void shouldSetValidToToCurrentTime() {
      final var beforeCall = LocalDateTime.now().minusSeconds(1);
      final var result = StaffVersionEntityMapper.toStaffVersion(staffEntity);
      final var afterCall = LocalDateTime.now().plusSeconds(1);

      assertNotNull(result.getValidTo());
      assertTrue(result.getValidTo().isAfter(beforeCall));
      assertTrue(result.getValidTo().isBefore(afterCall));
    }

    @Test
    void shouldCopyPaTitles() {
      final var result = StaffVersionEntityMapper.toStaffVersion(staffEntity);

      assertEquals(1, result.getPaTitles().size());
      assertEquals("CODE1", result.getPaTitles().get(0).getCode());
      assertEquals("Description1", result.getPaTitles().get(0).getDescription());
    }

    @Test
    void shouldCopySpecialities() {
      final var result = StaffVersionEntityMapper.toStaffVersion(staffEntity);

      assertEquals(1, result.getSpecialities().size());
      assertEquals("Cardiology", result.getSpecialities().get(0).getSpeciality());
    }

    @Test
    void shouldCopyHealthcareProfessionalLicences() {
      final var result = StaffVersionEntityMapper.toStaffVersion(staffEntity);

      assertEquals(1, result.getHealthcareProfessionalLicences().size());
      assertEquals("LICENSE1",
          result.getHealthcareProfessionalLicences().get(0).getHealthcareProfessionalLicence());
    }

    @Test
    void shouldHandleNullPaTitles() {
      staffEntity.setPaTitles(null);

      final var result = StaffVersionEntityMapper.toStaffVersion(staffEntity);

      assertNotNull(result.getPaTitles());
      assertTrue(result.getPaTitles().isEmpty());
    }

    @Test
    void shouldHandleNullSpecialities() {
      staffEntity.setSpecialities(null);

      final var result = StaffVersionEntityMapper.toStaffVersion(staffEntity);

      assertNotNull(result.getSpecialities());
      assertTrue(result.getSpecialities().isEmpty());
    }

    @Test
    void shouldHandleNullHealthcareProfessionalLicences() {
      staffEntity.setHealthcareProfessionalLicences(null);

      final var result = StaffVersionEntityMapper.toStaffVersion(staffEntity);

      assertNotNull(result.getHealthcareProfessionalLicences());
      assertTrue(result.getHealthcareProfessionalLicences().isEmpty());
    }
  }

  @Nested
  class ToStaffTests {

    @BeforeEach
    void setUp() {
      staffVersionEntity = StaffVersionEntity.builder()
          .key(1)
          .hsaId("HSA123")
          .firstName("John")
          .middleName("Middle")
          .lastName("Doe")
          .validFrom(LocalDateTime.now().minusDays(1))
          .validTo(LocalDateTime.now())
          .role(staffRole)
          .paTitles(List.of(new PaTitleVersionEmbeddable("CODE1", "Description1")))
          .specialities(List.of(new SpecialityVersionEmbeddable("Cardiology")))
          .healthcareProfessionalLicences(
              List.of(new HealthcareProfessionalLicenceVersionEmbeddable("LICENSE1")))
          .staff(null)
          .build();
    }

    @Test
    void shouldMapStaffVersionEntityToStaffEntity() {
      final var result = StaffVersionEntityMapper.toStaff(staffVersionEntity);

      assertEquals(staffVersionEntity.getHsaId(), result.getHsaId());
      assertEquals(staffVersionEntity.getFirstName(), result.getFirstName());
      assertEquals(staffVersionEntity.getMiddleName(), result.getMiddleName());
      assertEquals(staffVersionEntity.getLastName(), result.getLastName());
      assertEquals(staffVersionEntity.getRole(), result.getRole());
    }

    @Test
    void shouldCopyPaTitles() {
      final var result = StaffVersionEntityMapper.toStaff(staffVersionEntity);

      assertEquals(1, result.getPaTitles().size());
      assertEquals("CODE1", result.getPaTitles().get(0).getCode());
      assertEquals("Description1", result.getPaTitles().get(0).getDescription());
    }

    @Test
    void shouldCopySpecialities() {
      final var result = StaffVersionEntityMapper.toStaff(staffVersionEntity);

      assertEquals(1, result.getSpecialities().size());
      assertEquals("Cardiology", result.getSpecialities().get(0).getSpeciality());
    }

    @Test
    void shouldCopyHealthcareProfessionalLicences() {
      final var result = StaffVersionEntityMapper.toStaff(staffVersionEntity);

      assertEquals(1, result.getHealthcareProfessionalLicences().size());
      assertEquals("LICENSE1",
          result.getHealthcareProfessionalLicences().get(0).getHealthcareProfessionalLicence());
    }

    @Test
    void shouldHandleNullPaTitles() {
      staffVersionEntity.setPaTitles(null);

      final var result = StaffVersionEntityMapper.toStaff(staffVersionEntity);

      assertNotNull(result.getPaTitles());
      assertTrue(result.getPaTitles().isEmpty());
    }

    @Test
    void shouldHandleNullSpecialities() {
      staffVersionEntity.setSpecialities(null);

      final var result = StaffVersionEntityMapper.toStaff(staffVersionEntity);

      assertNotNull(result.getSpecialities());
      assertTrue(result.getSpecialities().isEmpty());
    }

    @Test
    void shouldHandleNullHealthcareProfessionalLicences() {
      staffVersionEntity.setHealthcareProfessionalLicences(null);

      final var result = StaffVersionEntityMapper.toStaff(staffVersionEntity);

      assertNotNull(result.getHealthcareProfessionalLicences());
      assertTrue(result.getHealthcareProfessionalLicences().isEmpty());
    }
  }
}