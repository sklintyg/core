package se.inera.intyg.certificateservice.application.common;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class ValidationUtil {

  private ValidationUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static void validatePatient(PatientDTO patient) {
    if (patient == null) {
      throw new IllegalArgumentException("Required parameter missing: Patient");
    }
    if (patient.getId() == null) {
      throw new IllegalArgumentException("Required parameter missing: Patient.id");
    }
    if (patient.getId().getId() == null || patient.getId().getId().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: Patient.id.id");
    }
    if (patient.getId().getType() == null) {
      throw new IllegalArgumentException("Required parameter missing: Patient.id.type");
    }
    if (patient.getDeceased() == null) {
      throw new IllegalArgumentException("Required parameter missing: Patient.deceased");
    }
    if (patient.getProtectedPerson() == null) {
      throw new IllegalArgumentException("Required parameter missing: Patient.protectedPerson");
    }
    if (patient.getTestIndicated() == null) {
      throw new IllegalArgumentException("Required parameter missing: Patient.testIndicated");
    }
  }

  public static void validateUnitExtended(UnitDTO unit, String parameter) {
    validateUnit(unit, parameter);
    if (unit.getInactive() == null) {
      throw new IllegalArgumentException(
          "Required parameter missing: %s.isInactive".formatted(parameter));
    }
  }

  public static void validateUnit(UnitDTO unit, String parameter) {
    if (unit == null) {
      throw new IllegalArgumentException("Required parameter missing: %s".formatted(parameter));
    }
    if (unit.getId() == null || unit.getId().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: %s.id".formatted(parameter));
    }
  }

  public static void validateUser(UserDTO user) {
    if (user == null) {
      throw new IllegalArgumentException("Required parameter missing: User");
    }
    if (user.getId() == null || user.getId().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: User.id");
    }
    if (user.getRole() == null) {
      throw new IllegalArgumentException("Required parameter missing: User.role");
    }
    if (user.getBlocked() == null) {
      throw new IllegalArgumentException("Required parameter missing: User.blocked");
    }
  }

  public static void validateCertificateId(CertificateModelIdDTO certificateModelIdDTO) {
    if (certificateModelIdDTO == null) {
      throw new IllegalArgumentException("Required parameter missing: CertificateModelId");
    }
    if (certificateModelIdDTO.getType() == null || certificateModelIdDTO.getType().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: CertificateModelId.type");
    }
    if (certificateModelIdDTO.getVersion() == null || certificateModelIdDTO.getVersion()
        .isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: CertificateModelId.version");
    }
  }
}
