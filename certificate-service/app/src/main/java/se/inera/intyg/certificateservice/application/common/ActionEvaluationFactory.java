package se.inera.intyg.certificateservice.application.common;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Blocked;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Inactive;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitName;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonAddress;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.domain.user.model.User;

@Component
public class ActionEvaluationFactory {

  public ActionEvaluation create(UserDTO user, UnitDTO unit, UnitDTO careUnit,
      UnitDTO careProvider) {
    return create(null, user, unit, careUnit, careProvider);
  }

  public ActionEvaluation create(PatientDTO patient, UserDTO user, UnitDTO unit, UnitDTO careUnit,
      UnitDTO careProvider) {
    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .hsaId(
                    new HsaId(user.getId())
                )
                .name(
                    Name.builder()
                        .lastName(user.getName())
                        .build()
                )
                .blocked(new Blocked(user.getBlocked()))
                .build()
        )
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(unit.getId()))
                .name(new UnitName(unit.getName()))
                .address(
                    UnitAddress.builder()
                        .address(unit.getAddress())
                        .zipCode(unit.getZipCode())
                        .city(unit.getCity())
                        .build()
                )
                .contactInfo(
                    UnitContactInfo.builder()
                        .phoneNumber(unit.getPhoneNumber())
                        .email(unit.getEmail())
                        .build()
                )
                .inactive(new Inactive(unit.getInactive()))
                .build()
        )
        .careUnit(
            CareUnit.builder()
                .hsaId(new HsaId(careUnit.getId()))
                .name(new UnitName(careUnit.getName()))
                .address(
                    UnitAddress.builder()
                        .address(careUnit.getAddress())
                        .zipCode(careUnit.getZipCode())
                        .city(careUnit.getCity())
                        .build()
                )
                .contactInfo(
                    UnitContactInfo.builder()
                        .phoneNumber(careUnit.getPhoneNumber())
                        .email(careUnit.getEmail())
                        .build()
                )
                .build()
        )
        .careProvider(
            CareProvider.builder()
                .hsaId(new HsaId(careProvider.getId()))
                .name(new UnitName(careProvider.getName()))
                .address(
                    UnitAddress.builder()
                        .address(careProvider.getAddress())
                        .zipCode(careProvider.getZipCode())
                        .city(careProvider.getCity())
                        .build()
                )
                .contactInfo(
                    UnitContactInfo.builder()
                        .phoneNumber(careProvider.getPhoneNumber())
                        .email(careProvider.getEmail())
                        .build()
                )
                .build()
        );
    if (patient != null) {
      patient(actionEvaluation, patient);
    }
    return actionEvaluation.build();
  }

  private void patient(ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder,
      PatientDTO patient) {
    actionEvaluationBuilder.patient(
        Patient.builder()
            .id(
                PersonId.builder()
                    .type(convertType(patient.getId().getType()))
                    .id(patient.getId().getId())
                    .build()
            )
            .name(
                Name.builder()
                    .firstName(patient.getFirstName())
                    .middleName(patient.getMiddleName())
                    .lastName(patient.getLastName())
                    .build()
            )
            .deceased(new Deceased(patient.getDeceased()))
            .address(
                PersonAddress.builder()
                    .city(patient.getCity())
                    .street(patient.getStreet())
                    .zipCode(patient.getZipCode())
                    .build()
            )
            .protectedPerson(new ProtectedPerson(patient.getProtectedPerson()))
            .testIndicated(new TestIndicated(patient.getTestIndicated()))
            .build()
    );
  }

  private PersonIdType convertType(PersonIdTypeDTO type) {
    return switch (type) {
      case PERSONAL_IDENTITY_NUMBER -> PersonIdType.PERSONAL_IDENTITY_NUMBER;
      case COORDINATION_NUMBER -> PersonIdType.COORDINATION_NUMBER;
    };
  }
}
