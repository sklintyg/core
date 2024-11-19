package se.inera.intyg.certificateservice.application.common.dto;

import se.inera.intyg.certificateservice.domain.common.model.Role;

public enum RoleTypeDTO {
  DOCTOR,
  PRIVATE_DOCTOR,
  NURSE,
  MIDWIFE,
  CARE_ADMIN,
  DENTIST;

  public Role toRole() {
    return switch (this) {
      case DOCTOR -> Role.DOCTOR;
      case NURSE -> Role.NURSE;
      case MIDWIFE -> Role.MIDWIFE;
      case CARE_ADMIN -> Role.CARE_ADMIN;
      case DENTIST -> Role.DENTIST;
      case PRIVATE_DOCTOR -> Role.PRIVATE_DOCTOR;
    };
  }

  public static RoleTypeDTO toRoleType(Role role) {
    return switch (role) {
      case DOCTOR -> DOCTOR;
      case NURSE -> NURSE;
      case MIDWIFE -> MIDWIFE;
      case CARE_ADMIN -> CARE_ADMIN;
      case DENTIST -> DENTIST;
      case PRIVATE_DOCTOR -> PRIVATE_DOCTOR;
    };
  }
}
