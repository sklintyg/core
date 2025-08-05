package se.inera.intyg.certificateservice.application.common;

import static se.inera.intyg.certificateservice.domain.common.model.AccessScope.WITHIN_CARE_UNIT;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.common.model.Agreement;
import se.inera.intyg.certificateservice.domain.common.model.AllowCopy;
import se.inera.intyg.certificateservice.domain.common.model.Blocked;
import se.inera.intyg.certificateservice.domain.common.model.HealthCareProfessionalLicence;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonAddress;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.Inactive;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.unit.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.unit.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;
import se.inera.intyg.certificateservice.domain.user.model.ResponsibleIssuer;
import se.inera.intyg.certificateservice.domain.user.model.SrsActive;
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
                        .firstName(user.getFirstName())
                        .middleName(user.getMiddleName())
                        .lastName(user.getLastName())
                        .build()
                )
                .blocked(new Blocked(user.getBlocked()))
                .agreement(new Agreement(user.getAgreement()))
                .allowCopy(new AllowCopy(user.getAllowCopy()))
                .role(user.getRole().toRole())
                .paTitles(
                    user.getPaTitles().stream()
                        .map(paTitleDTO ->
                            new PaTitle(paTitleDTO.getCode(), paTitleDTO.getDescription())
                        )
                        .toList()
                )
                .specialities(
                    user.getSpecialities().stream()
                        .map(Speciality::new)
                        .toList()
                )
                .accessScope(user.getAccessScope() == null ? WITHIN_CARE_UNIT
                    : user.getAccessScope().toDomain()
                )
                .healthCareProfessionalLicence(
                    user.getHealthCareProfessionalLicence().stream()
                        .map(HealthCareProfessionalLicence::new)
                        .toList()
                )
                .responsibleIssuer(new ResponsibleIssuer(user.getResponsibleHospName()))
                .srsActive(new SrsActive(user.getSrsActive()))
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
                .workplaceCode(new WorkplaceCode(unit.getWorkplaceCode()))
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
                .workplaceCode(new WorkplaceCode(unit.getWorkplaceCode()))
                .build()
        )
        .careProvider(
            CareProvider.builder()
                .hsaId(new HsaId(careProvider.getId()))
                .name(new UnitName(careProvider.getName()))
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
                    .type(patient.getId().getType().toPersonIdType())
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
}