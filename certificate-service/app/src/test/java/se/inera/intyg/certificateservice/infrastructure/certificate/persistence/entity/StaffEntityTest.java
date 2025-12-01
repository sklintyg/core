package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.ajlaDoctorEntityBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_HSA_ID;

import java.util.List;
import org.junit.jupiter.api.Test;

class StaffEntityTest {

  @Test
  void shallUpdateFields() {
    var target = ajlaDoctorEntityBuilder().build();
    var originalHsaId = target.getHsaId();
    var originalKey = target.getKey();

    var source = ajlaDoctorEntityBuilder()
        .firstName("UpdatedFirst")
        .middleName("UpdatedMiddle")
        .lastName("UpdatedLast")
        .role(new StaffRoleEntity())
        .paTitles(List.of(new PaTitleEmbeddable()))
        .specialities(List.of(new SpecialityEmbeddable()))
        .healthcareProfessionalLicences(List.of(new HealthcareProfessionalLicenceEmbeddable()))
        .build();

    target.updateWith(source);

    assertEquals("UpdatedFirst", target.getFirstName());
    assertEquals("UpdatedMiddle", target.getMiddleName());
    assertEquals("UpdatedLast", target.getLastName());
    assertSame(source.getRole(), target.getRole(), "Role should be replaced");
    assertSame(source.getPaTitles(), target.getPaTitles(),
        "paTitles list reference should be replaced");
    assertSame(source.getSpecialities(), target.getSpecialities(),
        "specialities list reference should be replaced");
    assertSame(source.getHealthcareProfessionalLicences(),
        target.getHealthcareProfessionalLicences(),
        "licences list reference should be replaced");
    assertEquals(originalHsaId, target.getHsaId(), "hsaId must remain unchanged");
    assertEquals(originalKey, target.getKey(), "key must remain unchanged");
  }

  @Test
  void shallThrowExceptionWhenSourceIsNull() {
    assertThrows(IllegalArgumentException.class, () -> AJLA_DOKTOR_ENTITY.updateWith(null));
  }

  @Test
  void shallNotFindDiffWhenEntityHasSameValues() {
    assertFalse(AJLA_DOKTOR_ENTITY.hasDiff(ajlaDoctorEntityBuilder().build()));
  }

  @Test
  void shallFindDiffWhenEntityHasDifferentValues() {
    assertTrue(AJLA_DOKTOR_ENTITY.hasDiff(ajlaDoctorEntityBuilder()
        .firstName("DifferentName")
        .build()));
  }


  @Test
  void shallFindDiffWhenFirstNameDiffers() {
    assertTrue(AJLA_DOKTOR_ENTITY.hasDiff(
        ajlaDoctorEntityBuilder().firstName("DifferentFirst").build()), "firstName");
  }

  @Test
  void shallFindDiffWhenMiddleNameDiffers() {
    assertTrue(AJLA_DOKTOR_ENTITY.hasDiff(
        ajlaDoctorEntityBuilder().middleName("DifferentMiddle").build()), "middleName");
  }

  @Test
  void shallFindDiffWhenLastNameDiffers() {
    assertTrue(AJLA_DOKTOR_ENTITY.hasDiff(
        ajlaDoctorEntityBuilder().lastName("DifferentLast").build()), "lastName");
  }

  @Test
  void shallFindDiffWhenRoleDiffers() {
    assertTrue(AJLA_DOKTOR_ENTITY.hasDiff(
        ajlaDoctorEntityBuilder().role(new StaffRoleEntity()).build()), "role");
  }

  @Test
  void shallFindDiffWhenPaTitlesDiffer() {
    assertTrue(AJLA_DOKTOR_ENTITY.hasDiff(
        ajlaDoctorEntityBuilder().paTitles(List.of(new PaTitleEmbeddable())).build()), "paTitles");
  }


  @Test
  void shallFindDiffWhenSpecialitiesDiffer() {
    assertTrue(AJLA_DOKTOR_ENTITY.hasDiff(
            ajlaDoctorEntityBuilder().specialities(List.of(new SpecialityEmbeddable())).build()),
        "specialities");
  }

  @Test
  void shallFindDiffWhenHealthcareProfessionalLicencesDiffer() {
    assertTrue(AJLA_DOKTOR_ENTITY.hasDiff(
            ajlaDoctorEntityBuilder()
                .healthcareProfessionalLicences(List.of(new HealthcareProfessionalLicenceEmbeddable()))
                .build()),
        "healthcareProfessionalLicences");
  }

  @Test
  void shallFindDiffWhenAllComparedFieldsDiffer() {
    assertTrue(AJLA_DOKTOR_ENTITY.hasDiff(
            ajlaDoctorEntityBuilder()
                .firstName("F")
                .middleName("M")
                .lastName("L")
                .role(new StaffRoleEntity())
                .paTitles(List.of(new PaTitleEmbeddable()))
                .specialities(List.of(new SpecialityEmbeddable()))
                .healthcareProfessionalLicences(
                    List.of(new HealthcareProfessionalLicenceEmbeddable()))
                .build()),
        "all fields changed");
  }

  @Test
  void shallNotThrowExceptionWhenComparingStaffWithCaseDifferentHsaId() {
    assertFalse(AJLA_DOKTOR_ENTITY.hasDiff(
            ajlaDoctorEntityBuilder()
                .hsaId(AJLA_DOCTOR_HSA_ID.toLowerCase())
                .build()),
        "no fields changed");
  }


  @Test
  void shallThrowExceptionWhenOtherIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> AJLA_DOKTOR_ENTITY.hasDiff(null));
  }

  @Test
  void shallThrowExceptionWhenComparingStaffWithDifferentHsaId() {
    var staffWithDifferentHsaId = ajlaDoctorEntityBuilder()
        .hsaId(ALF_DOKTOR_HSA_ID)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> AJLA_DOKTOR_ENTITY.hasDiff(staffWithDifferentHsaId));
  }

}