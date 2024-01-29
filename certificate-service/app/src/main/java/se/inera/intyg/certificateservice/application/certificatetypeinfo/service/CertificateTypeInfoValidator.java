package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@Component
public class CertificateTypeInfoValidator {

  public void validate(GetCertificateTypeInfoRequest getCertificateTypeInfoRequest) {
    validateUser(getCertificateTypeInfoRequest.getUser());
    validateUnitExtended(getCertificateTypeInfoRequest.getUnit(), "Unit");
    validateUnit(getCertificateTypeInfoRequest.getCareUnit(), "CareUnit");
    validateUnit(getCertificateTypeInfoRequest.getCareProvider(), "CareProvider");
    validatePatient(getCertificateTypeInfoRequest.getPatient());
  }

  private void validatePatient(PatientDTO patient) {
    if (patient == null) {
      throw new IllegalArgumentException("Required parameter missing: Patient");
    }
    if (patient.getId() == null) {
      throw new IllegalArgumentException("Required parameter missing: Patient.id");
    }
    if (patient.getId().getId() == null || patient.getId().getId().isEmpty()) {
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

  private void validateUnitExtended(UnitDTO unit, String parameter) {
    validateUnit(unit, parameter);
    if (unit.getInactive() == null) {
      throw new IllegalArgumentException(
          "Required parameter missing: %s.isInactive".formatted(parameter));
    }
  }

  private static void validateUnit(UnitDTO unit, String parameter) {
    if (unit == null) {
      throw new IllegalArgumentException("Required parameter missing: %s".formatted(parameter));
    }
    if (unit.getId() == null || unit.getId().isEmpty()) {
      throw new IllegalArgumentException("Required parameter missing: %s.id".formatted(parameter));
    }
  }

  private static void validateUser(UserDTO user) {
    if (user == null) {
      throw new IllegalArgumentException("Required parameter missing: User");
    }
    if (user.getId() == null || user.getId().isEmpty()) {
      throw new IllegalArgumentException("Required parameter missing: User.id");
    }
    if (user.getRole() == null) {
      throw new IllegalArgumentException("Required parameter missing: User.role");
    }
    if (user.getBlocked() == null) {
      throw new IllegalArgumentException("Required parameter missing: User.blocked");
    }
  }
}
