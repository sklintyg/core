package se.inera.intyg.certificateservice.application.common.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.Role;

class RoleTypeDTOTest {

  @Test
  void shallReturnRoleDoctor() {
    assertEquals(Role.DOCTOR, RoleTypeDTO.DOCTOR.toRole());
  }

  @Test
  void shallReturnRoleNurse() {
    assertEquals(Role.NURSE, RoleTypeDTO.NURSE.toRole());
  }

  @Test
  void shallReturnRoleDentist() {
    assertEquals(Role.DENTIST, RoleTypeDTO.DENTIST.toRole());
  }

  @Test
  void shallReturnRoleMidwife() {
    assertEquals(Role.MIDWIFE, RoleTypeDTO.MIDWIFE.toRole());
  }

  @Test
  void shallReturnRoleCareAdmin() {
    assertEquals(Role.CARE_ADMIN, RoleTypeDTO.CARE_ADMIN.toRole());
  }

  @Test
  void shallReturnRoleTypeDTODoctor() {
    assertEquals(RoleTypeDTO.DOCTOR, RoleTypeDTO.toRoleType(Role.DOCTOR));
  }

  @Test
  void shallReturnRoleTypeDTONurse() {
    assertEquals(RoleTypeDTO.NURSE, RoleTypeDTO.toRoleType(Role.NURSE));
  }

  @Test
  void shallReturnRoleTypeDTODentist() {
    assertEquals(RoleTypeDTO.DENTIST, RoleTypeDTO.toRoleType(Role.DENTIST));
  }

  @Test
  void shallReturnRoleTypeDTOMidwife() {
    assertEquals(RoleTypeDTO.MIDWIFE, RoleTypeDTO.toRoleType(Role.MIDWIFE));
  }

  @Test
  void shallReturnRoleTypeDTOCareAdmin() {
    assertEquals(RoleTypeDTO.CARE_ADMIN, RoleTypeDTO.toRoleType(Role.CARE_ADMIN));
  }
}