package se.inera.intyg.certificateservice.application.common.dto;

import se.inera.intyg.certificateservice.domain.user.model.UserRole;

public enum RoleTypeDTO {
  DOCTOR,
  NURSE,
  MIDWIFE,
  CARE_ADMIN,
  DENTIST;

  public UserRole toUserRole() {
    return switch (this) {
      case DOCTOR -> UserRole.DOCTOR;
      case NURSE -> UserRole.NURSE;
      case MIDWIFE -> UserRole.MIDWIFE;
      case CARE_ADMIN -> UserRole.CARE_ADMIN;
      case DENTIST -> UserRole.DENTIST;
    };
  }

  public static RoleTypeDTO toRoleType(UserRole userRole) {
    return switch (userRole) {
      case DOCTOR -> DOCTOR;
      case NURSE -> NURSE;
      case MIDWIFE -> MIDWIFE;
      case CARE_ADMIN -> CARE_ADMIN;
      case DENTIST -> DENTIST;
    };
  }
}
