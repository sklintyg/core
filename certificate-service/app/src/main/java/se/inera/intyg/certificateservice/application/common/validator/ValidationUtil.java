package se.inera.intyg.certificateservice.application.common.validator;

import java.util.List;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PrefillXmlDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeInformationDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason;

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
    if (unit.getWorkplaceCode() == null) {
      throw new IllegalArgumentException(
          "Required parameter missing: %s.workplaceCode".formatted(parameter));
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
    if (user.getFirstName() == null || user.getFirstName().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: User.firstName");
    }
    if (user.getLastName() == null || user.getLastName().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: User.lastName");
    }
    if (user.getRole() == null) {
      throw new IllegalArgumentException("Required parameter missing: User.role");
    }
    if (user.getBlocked() == null) {
      throw new IllegalArgumentException("Required parameter missing: User.blocked");
    }
    if (user.getAgreement() == null) {
      throw new IllegalArgumentException("Required parameter missing: User.agreement");
    }
    if (user.getAllowCopy() == null) {
      throw new IllegalArgumentException("Required parameter missing: User.allowCopy");
    }
    if (user.getHealthCareProfessionalLicence() == null) {
      throw new IllegalArgumentException(
          "Required parameter missing: User.healthCareProfessionalLicence"
      );
    }
  }

  public static void validateCertificateModelId(CertificateModelIdDTO certificateModelIdDTO) {
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

  public static void validateCertificateId(String certificateId) {
    if (certificateId == null || certificateId.isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: certificateId");
    }
  }

  public static void validateMessageId(String messageId) {
    if (messageId == null || messageId.isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: messageId");
    }
  }

  public static void validateMessage(String message) {
    if (message == null || message.isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: message");
    }
  }

  public static void validateRevokeInformation(RevokeInformationDTO revokeInformation) {
    if (revokeInformation.getReason() == null || revokeInformation.getReason().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: revoke.reason");
    }

    if (RevokedReason.valueOf(revokeInformation.getReason())
        .equals(RevokedReason.OTHER_SERIOUS_ERROR) && (revokeInformation.getMessage() == null
        || revokeInformation.getMessage().isBlank())) {
      throw new IllegalArgumentException("Required parameter missing: revoke.message");
    }
  }

  public static void validateAdditonalInfoText(String additonalInfoText) {
    if (additonalInfoText == null || additonalInfoText.isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: additionalInfoText");
    }
  }

  public static void validateVersion(Long version) {
    if (version == null) {
      throw new IllegalArgumentException("Required parameter missing: version");
    }
  }

  public static void validateCertificate(CertificateDTO certificateDTO) {
    if (certificateDTO == null) {
      throw new IllegalArgumentException("Required parameter missing: Certificate");
    }
    if (certificateDTO.getMetadata() == null) {
      throw new IllegalArgumentException("Required parameter missing: Certificate.metadata");
    }

    if (certificateDTO.getData() == null) {
      throw new IllegalArgumentException("Required parameter missing: Certificate.data");
    }
  }

  public static void validatePersonId(PersonIdDTO personId) {
    if (personId == null) {
      throw new IllegalArgumentException("Required parameter missing: PersonId");
    }
    if (personId.getId() == null || personId.getId().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: PersonId.id");
    }
    if (personId.getType() == null) {
      throw new IllegalArgumentException("Required parameter missing: PersonId.type");
    }
  }

  public static void validateQuestion(QuestionDTO question) {
    if (question == null) {
      throw new IllegalArgumentException("Required parameter missing: Question");
    }
    if (question.getType() == null) {
      throw new IllegalArgumentException("Required parameter missing: Question.type");
    }
  }

  public static <T> void validateList(List<T> list, String parameter) {
    if (list == null || list.isEmpty()) {
      throw new IllegalArgumentException("Required parameter missing: %s".formatted(parameter));
    }
  }

  public static void validatePrefillXml(PrefillXmlDTO prefillXmlDTO) {
    if (prefillXmlDTO == null || prefillXmlDTO.value() == null || prefillXmlDTO.value().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: PrefillXml");
    }
  }
}